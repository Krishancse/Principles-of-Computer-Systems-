import jpcap.*;
import jpcap.packet.Packet;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class NetworkAnalyzer {

    public static void main(String[] args) {
        try {
            NetworkInterface[] devices = JpcapCaptor.getDeviceList();

            if (devices.length == 0) {
                System.out.println("No network interface found.");
                return;
            }

            NetworkInterface device = devices[0]; // Choose the first network interface

            JpcapCaptor captor = JpcapCaptor.openDevice(device, 65535, true, 20);

            captor.setPacketReadTimeout(1000); // Set the packet read timeout in milliseconds

            while (true) {
                Packet packet = captor.getPacket();
                if (packet == null) continue;

                // Print basic packet information
                System.out.println("Captured packet:");
                System.out.println("Length: " + packet.caplen);
                System.out.println("Data: " + new String(packet.data));

                // Decode Ethernet packet
                if (packet instanceof EthernetPacket) {
                    EthernetPacket ethPacket = (EthernetPacket) packet;
                    System.out.println("Ethernet Source MAC: " + ethPacket.getSourceAddress());
                    System.out.println("Ethernet Destination MAC: " + ethPacket.getDestinationAddress());
                }

                // Decode IP packet
                if (packet instanceof IPPacket) {
                    IPPacket ipPacket = (IPPacket) packet;
                    System.out.println("Source IP: " + ipPacket.src_ip);
                    System.out.println("Destination IP: " + ipPacket.dst_ip);
                }

                // Decode TCP packet
                if (packet instanceof TCPPacket) {
                    TCPPacket tcpPacket = (TCPPacket) packet;
                    System.out.println("Source Port: " + tcpPacket.src_port);
                    System.out.println("Destination Port: " + tcpPacket.dst_port);
                }

                // Decode UDP packet
                if (packet instanceof UDPPacket) {
                    UDPPacket udpPacket = (UDPPacket) packet;
                    System.out.println("Source Port: " + udpPacket.src_port);
                    System.out.println("Destination Port: " + udpPacket.dst_port);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

