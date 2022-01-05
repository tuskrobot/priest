package com.tusk.priest.server.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class IpUtil {
    private IpUtil() {
    }

    /**
     * 此方法描述的是：获得服务器的IP地址
     *
     * @author: zhangyang33@sinopharm.com
     * @version: 2014年9月5日 下午4:57:15
     */
    public static String getLocalIP() throws SocketException {
        String sIP = "";
        InetAddress ip = null;
        boolean bFindIP = false;
        Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
                .getNetworkInterfaces();
        while (netInterfaces.hasMoreElements()) {
            if (bFindIP) {
                break;
            }
            NetworkInterface ni = (NetworkInterface) netInterfaces
                    .nextElement();
            Enumeration<InetAddress> ips = ni.getInetAddresses();
            while (ips.hasMoreElements()) {
                ip = (InetAddress) ips.nextElement();
                if (!ip.isLoopbackAddress()
                        && ip.getHostAddress().matches(
                        "(\\d{1,3}\\.){3}\\d{1,3}")) {
                    bFindIP = true;
                    break;
                }
            }
        }
        if (null != ip) {
            sIP = ip.getHostAddress();
        }
        return sIP;
    }

    /**
     * 此方法描述的是：获得服务器的IP地址(多网卡)
     *
     * @author: zhangyang33@sinopharm.com
     * @version: 2014年9月5日 下午4:57:15
     */
    public static List<String> getLocalIPS() throws SocketException {
        InetAddress ip = null;
        List<String> ipList = new ArrayList<String>();
        Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
                .getNetworkInterfaces();
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) netInterfaces
                    .nextElement();
            Enumeration<InetAddress> ips = ni.getInetAddresses();
            while (ips.hasMoreElements()) {
                ip = (InetAddress) ips.nextElement();
                if (!ip.isLoopbackAddress()
                        && ip.getHostAddress().matches(
                        "(\\d{1,3}\\.){3}\\d{1,3}")) {
                    ipList.add(ip.getHostAddress());
                }
            }
        }

        return ipList;
    }

    /**
     * 此方法描述的是：获得服务器的MAC地址
     *
     * @author: zhangyang33@sinopharm.com
     * @version: 2014年9月5日 下午1:27:25
     */
    public static String getMacId() throws SocketException {
        String macId = "";
        InetAddress ip = null;
        NetworkInterface ni = null;
        boolean bFindIP = false;
        Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
                .getNetworkInterfaces();
        while (netInterfaces.hasMoreElements()) {
            if (bFindIP) {
                break;
            }
            ni = (NetworkInterface) netInterfaces
                    .nextElement();
            // ----------特定情况，可以考虑用ni.getName判断
            // 遍历所有ip
            Enumeration<InetAddress> ips = ni.getInetAddresses();
            while (ips.hasMoreElements()) {
                ip = (InetAddress) ips.nextElement();
                if (!ip.isLoopbackAddress() // 非127.0.0.1
                        && ip.getHostAddress().matches(
                        "(\\d{1,3}\\.){3}\\d{1,3}")) {
                    bFindIP = true;
                    break;
                }
            }
        }

        if (null != ip) {
            macId = getMacFromBytes(ni.getHardwareAddress());

        }
        return macId;
    }

    /**
     * 此方法描述的是：获得服务器的MAC地址(多网卡)
     *
     * @author: zhangyang33@sinopharm.com
     * @version: 2014年9月5日 下午1:27:25
     */
    public static List<String> getMacIds() throws SocketException {
        InetAddress ip = null;
        NetworkInterface ni = null;
        List<String> macList = new ArrayList<String>();
        Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface
                .getNetworkInterfaces();
        while (netInterfaces.hasMoreElements()) {
            ni = (NetworkInterface) netInterfaces
                    .nextElement();
            // ----------特定情况，可以考虑用ni.getName判断
            // 遍历所有ip
            Enumeration<InetAddress> ips = ni.getInetAddresses();
            while (ips.hasMoreElements()) {
                ip = (InetAddress) ips.nextElement();
                if (!ip.isLoopbackAddress() // 非127.0.0.1
                        && ip.getHostAddress().matches(
                        "(\\d{1,3}\\.){3}\\d{1,3}")) {
                    macList.add(getMacFromBytes(ni.getHardwareAddress()));
                }
            }
        }
        return macList;
    }

    private static String getMacFromBytes(byte[] bytes) {
        StringBuffer mac = new StringBuffer();
        byte currentByte;
        boolean first = false;
        for (byte b : bytes) {
            if (first) {
                mac.append("-");
            }
            currentByte = (byte) ((b & 240) >> 4);
            mac.append(Integer.toHexString(currentByte));
            currentByte = (byte) (b & 15);
            mac.append(Integer.toHexString(currentByte));
            first = true;
        }
        return mac.toString().toUpperCase();
    }
}
