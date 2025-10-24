package firstnetwork;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class FirstNetwork {
    public static void main(String[] args) {

        String target = (args.length > 0) ? args[0] : "localhost";

        InetAddress node, remotenode;
        byte[] ipaddr;

        try {
            // ===== LOCAL =====
            node = InetAddress.getLocalHost();
            System.out.println("Local address: " + node.getHostAddress());
            System.out.println("Local hostname: " + node.getHostName());
            System.out.println("Canonical name: " + node.getCanonicalHostName());
            System.out.println("IP bytes hash: " + Arrays.hashCode(node.getAddress()));
            System.out.println("Loopback: " + node.isLoopbackAddress());
            System.out.println("----------------------------------------");

            // ===== REMOTE (always by IP) =====
            byte[] remoteIpBytes;
            if (looksLikeIPv4(target)) {
                remoteIpBytes = parseIPv4(target);
            } else {
                InetAddress resolved = InetAddress.getByName(target);
                remoteIpBytes = resolved.getAddress();
            }
            remotenode = InetAddress.getByAddress(remoteIpBytes);

            System.out.println("Remote address: " + remotenode.getHostAddress());
            System.out.println("Remote hostname: " + remotenode.getHostName());
            System.out.println("Canonical name: " + remotenode.getCanonicalHostName());
            System.out.println("IP bytes hash: " + Arrays.hashCode(remotenode.getAddress()));
            System.out.println("Loopback: " + remotenode.isLoopbackAddress());
            System.out.println("Is multicast: " + remotenode.isMulticastAddress());

            try {
                boolean alive = remotenode.isReachable(1000);
                System.out.println("Reachable: " + alive);
            } catch (Exception e) { }
            System.out.println("----------------------------------------");

            // ===== /24 SCAN =====
            if (looksLikeIPv4(target)) {
                ipaddr = parseIPv4(target);
            } else {
                ipaddr = InetAddress.getLocalHost().getAddress();
            }

            if (ipaddr != null && ipaddr.length == 4) {
                int b0 = ipaddr[0] & 0xFF, b1 = ipaddr[1] & 0xFF, b2 = ipaddr[2] & 0xFF;
                System.out.println("Scanning network: " + b0 + "." + b1 + "." + b2 + ".0/24");
                for (int i = 1; i <= 254; i++) {
                    byte[] probe = new byte[] { (byte)b0, (byte)b1, (byte)b2, (byte)i };
                    try {
                        InetAddress host = InetAddress.getByAddress(probe);
                        if (host.isReachable(300)) {
                            System.out.println("ACTIVE: " + host.getHostAddress() +
                                    " (" + host.getHostName() + ")");
                        }
                    } catch (Exception e) { }
                }
            } else {
                System.out.println("Not IPv4 — skipping /24 scan.");
            }

        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean looksLikeIPv4(String s) {
        String[] parts = s.split("\\.");
        if (parts.length != 4) return false;
        for (String p : parts) {
            try {
                int v = Integer.parseInt(p);
                if (v < 0 || v > 255) return false;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return true;
    }

    private static byte[] parseIPv4(String s) {
        String[] parts = s.split("\\.");
        byte[] out = new byte[4];
        for (int i = 0; i < 4; i++) {
            int v = Integer.parseInt(parts[i]);
            out[i] = (byte) v;
        }
        return out;
    }
}
