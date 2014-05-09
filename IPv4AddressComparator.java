package playpen;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class IPv4AddressComparator implements Comparator<String> {

    @Override
    public int compare(String addr1, String addr2) {

        long baseAddress1 = 0;
        long baseAddress2 = 0;

        try {

            baseAddress1 = IPAddressUtil.ipToNumber(addr1);
            baseAddress2 = IPAddressUtil.ipToNumber(addr2);

        } catch (InvalidIPAddressException e) {
            System.out.println("Problem with IP: " + e.getMessage());
        }

        if (baseAddress1 == baseAddress2) {
            return 0;
        } else if (baseAddress1 < baseAddress2) {
            return -1;
        } else {
            return 1;
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        List<String> addresses = new ArrayList<>();

        addresses.add("192.168.0.0");
        addresses.add("2.168.0.1");
        addresses.add("0.0.0.1");
        addresses.add("192.168.0.1");
        addresses.add("92.68.0.1");
        addresses.add("255.0.0.0");
        addresses.add("0.0.0.0");
        addresses.add("255.255.255.254");

        for (String addr : addresses) {
            System.out.println("PRE SORT:  " + addr);
        }

        Collections.sort(addresses, new IPv4AddressComparator());
        System.out.println();

        for (String addr : addresses) {
            System.out.println("POST SORT:  " + addr);
        }

    }
}
