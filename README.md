# tcpip-virtual-networks

## Overview
Virtual network implementation over TCP/IP using Data Transfer and Dynamic Routing Protocols.  This is a Java-based implementation of a virtual network infrastructure, allowing hosts and routers to communicate using custom Data Transfer and Dynamic Routing Protocols. This project explores the fundamental concepts of networking, including host registration, dynamic routing, and efficient data transfer, within a simulated virtual environment.

## Protocols
This project implements custom protocols for communication. To fully understand the protocols and their specifications, please refer to the following RFC documents:

- **Dynamic Routing Protocol:** To understand the dynamic routing protocol used in this project, refer to [RFC0001.md](RFC0001.md).
- **Data Transfer Protocol:** To understand the data transfer protocol used in this project, refer to [RFC0002.md](RFC0002.md).

It is essential to review these RFC documents to grasp the underlying principles and specifications governing the communication between hosts and routers in this virtual network implementation.


## Key Features
- **Dynamic Routing Protocol:** Implements dynamic routing tables using the Bellman-Ford algorithm, enabling adaptive routing strategies and efficient packet forwarding.
- **Data Transfer Protocol:** Facilitates data exchange between hosts and routers, regulating communication and ensuring the secure transfer of information.
- **Host and Router Communication:** Hosts and routers can register, connect, and exchange data seamlessly within the virtual network.
- **Custom Packet Structure:** Utilizes a custom packet format to encapsulate data, source, destination, and next-hop information for efficient routing.

## Components
1. **IHost.java:** Interface defining host behavior, including registration and data transfer protocols.
2. **Host.java:** Implementation of the host, handling registration, connection to routers, and data migration.
3. **IRouter.java:** Interface outlining router functionality, including registration, host connections, and dynamic routing table updates.
4. **Router.java:** Router implementation, managing routing tables, connecting with hosts, and processing incoming packets.
5. **RouterEntry.java:** Class representing entries in the router's routing table, storing IP addresses, costs, and next-hop information.
6. **Packet.java:** Custom packet structure for encapsulating data, source, destination, and next-hop information.

## Usage
1. **Compile:** Compile the Java files using a Java compiler.
   ```
   javac *.java
   ```
2. **Run Host:** Start the host application to register, connect to routers, and send data.
   ```
   java Host
   ```
3. **Run Router:** Start the router application to register, manage routing tables, and facilitate host connections.
   ```
   java Router
   ```

## Additional Resources
- **Dynamic Routing Protocol RFC:** Detailed specification of the dynamic routing protocol used in the project.
- **Data Transfer Protocol RFC:** Specification outlining the data transfer protocol for secure communication.

## Contributors
- [Batoul Diab](https://github.com/batouldiab)

## License
This project is licensed under [MIT]. See the [LICENSE](LICENSE) file for details.
