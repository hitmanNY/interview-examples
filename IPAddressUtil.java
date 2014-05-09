
import java.util.ArrayList;
import java.util.regex.Pattern;

public class IPAddressUtil {
    private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
            + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public static long ipToNumber(String ipAddr) throws InvalidIPAddressException {
        if (ipAddr == null) {
            throw new InvalidIPAddressException("NULL IP Address");
        }

        String[] addrArray = ipAddr.split("\\.");

        if (addrArray.length != 4) {
            throw new InvalidIPAddressException("Invalid IP Address length");
        }

        long num = 0;
        for (int i = 0; i < addrArray.length; i++) {
            int power = 3 - i;

            int octet = Integer.parseInt(addrArray[i]);

            if (octet < 0 || octet > 255) {
                throw new InvalidIPAddressException("Invalid octet, it must be between 0-255");
            }

            num += ((octet % 256) * Math.pow(256, power));
        }
        return num;
    }

    public static String numberToIP(long addr) throws InvalidIPAddressException{

        if (addr < 0L || addr > 4294967295L) {
            throw new InvalidIPAddressException("Invalid IP");
        }

        StringBuilder ip = new StringBuilder();

        ip.append((addr >> 24) & 0xFF).append(".");
        ip.append((addr >> 16) & 0xFF).append(".");
        ip.append((addr >> 8) & 0xFF).append(".");
        ip.append((addr) & 0xFF);

        return ip.toString();
    }

    public static ArrayList<Long> getListOfNumericIPAddress(String ipAddress, int cidr) throws InvalidIPAddressException {

        return getListOfNumericIPAddress(ipToNumber(ipAddress), cidr);
    }

    public static ArrayList<Long> getListOfNumericIPAddress(long ipAddress, int cidr) throws InvalidIPAddressException {

        if (!isValidCidrblock(ipAddress, cidr))
            throw new InvalidIPAddressException("Invalid base IP Address for provided CIDR");

        ArrayList<Long> ips = new ArrayList<Long>();
        long blockSize = (long) Math.pow(2, (32 - cidr));
        for (long i = 0; i < blockSize; i++) {
            ips.add(ipAddress + i);
        }
        return ips;
    }

    public static ArrayList<String> getListOfIPAddress(String ipAddress, int cidr) throws InvalidIPAddressException {

        return getListOfIPAddress(ipToNumber(ipAddress), cidr);
    }

    public static ArrayList<String> getListOfIPAddress(long ipAddress, int cidr) throws InvalidIPAddressException {

        if (!isValidCidrblock(ipAddress, cidr)) {
            throw new InvalidIPAddressException("Invalid base IP Address for provided CIDR");
        }

        ArrayList<String> ips = new ArrayList<String>();
        long blockSize = (long) Math.pow(2, (32 - cidr));
        for (long i = 0; i < blockSize; i++) {
            ips.add(numberToIP(ipAddress + i));
        }
        return ips;
    }

    public static Boolean isValidCidrblock(final long ipAddress, final int cidr) throws InvalidIPAddressException {

        if (ipAddress == getCiderBaseIP(ipAddress, cidr)) {
            return true;
        }

        return false;
    }

    public static Boolean isValidCidrblock(final String ipAddress, final int cidr) throws InvalidIPAddressException {
        long address = IPAddressUtil.ipToNumber(ipAddress);
        return isValidCidrblock(address, cidr);
    }

    public static long getCiderBaseIP(final long ip, final int cidr) throws InvalidIPAddressException {

        long netmask = getSubnetMaskNumeric(cidr);

        // get base network ip for this ip/netmask combo
        long baseIP = ip & netmask;
        return baseIP;
    }

    public static String getSubnetMask(final int cidr) throws InvalidIPAddressException {
        return numberToIP(getSubnetMaskNumeric(cidr));
    }

    public static long getSubnetMaskNumeric(final int cidr) throws InvalidIPAddressException {

        if (cidr > 32 || cidr < 0)
            throw new InvalidIPAddressException("CIDR can not be greater than 32");

        // starting /24 netmask, in decimal (255.255.255.0)
        long netmask = 4294967040L;

        // calculating and correcting netmask
        if (cidr > 24) {
            for (long i = cidr; i > 24; i--) {
                netmask += (long) (java.lang.Math.pow(2, (32 - i)));
            }
        } else if (cidr < 24) {
            for (long i = cidr; i < 24; i++) {
                netmask -= (long) (java.lang.Math.pow(2, (32 - (i + 1))));
            }
        }

        return netmask;
    }

    public static boolean isValidAddressInNetblockString(String address, String netblock) throws InvalidIPAddressException {
        String[] parts = netblock.split("/");
        if (parts.length != 2) {
            throw new InvalidIPAddressException("Incorrectly formatted IP/CIDR string.");
        }
        String baseAddress = parts[0];
        int cidr;
        try {
            cidr = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new InvalidIPAddressException("Could not parse CIDR to integer: " + parts[1]);
        }
        return isValidAddressInNetblock(address, baseAddress, cidr);
    }

    public static boolean isValidAddressInNetblock(String address, String baseAddress, int cidr) throws InvalidIPAddressException {
        if (!isValidAddress(address)) {
            throw new InvalidIPAddressException("Invalid format for IP address.");
        } else if (!isValidAddress(baseAddress)) {
            throw new InvalidIPAddressException("Invalid format for base address in netblock.");
        } else if (!isValidCidrblock(baseAddress, cidr)) {
            throw new InvalidIPAddressException("Invalid netblock provided.");
        }

        long firstIp = ipToNumber(baseAddress);
        long givenIp = getCiderBaseIP(ipToNumber(address), cidr);
        return (givenIp == firstIp);
    }

    public static boolean isValidAddress(String address) {
        if (address == null) {
            return false;
        }

        return Pattern.compile(IPADDRESS_PATTERN).matcher(address).matches();
    }
}
