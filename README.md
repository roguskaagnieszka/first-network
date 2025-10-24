# 🖥️ First Network

A simple Java program that displays information about the local machine,  
checks the reachability of a remote IP address, and scans the local network  
to detect active hosts within the same subnet.

---

## ⚙️ Features

1. **Local host information**
    - IP address
    - Hostname and canonical name
    - IP bytes hash
    - Loopback status

2. **Remote host check (by IP only)**
    - Connects directly using `InetAddress.getByAddress()`
    - Displays remote IP information
    - Checks if the host is reachable (`isReachable()`)
    - Detects if the address is multicast

3. **Subnet scan (/24)**
    - Scans all addresses within the same subnet as the given IP
    - Prints a list of devices that respond as *ACTIVE*

---

## ▶️ How to Run

### 1. Compile
```bash
javac firstnetwork/FirstNetwork.java
