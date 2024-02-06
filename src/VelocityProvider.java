import com.oracle.tools.packager.mac.MacAppBundler;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.org.objectweb.asm.Handle;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.stream.Collectors;

interface VelocityProvider {

    /**
     * This method is called during the payment risk assessment.
     * <p>
     * It returns how many times the card in the Payment has been seen in the last minutes/seconds/hours as
     * defined in the {@code duration} parameter at the time the payment is being processed.
     *
     * @param payment  The payment being processed
     * @param duration The interval to count
     * @return The number of times the card was used in the interval defined in duration.
     */
    int getCardUsageCount(Payment payment, Duration duration);


    /**
     * After the payment is processed this method is called.
     *
     * @param payment The payment that has been processed.
     */
    void registerPayment(Payment payment);

    /**
     * @return Instance of a Velocity provider
     */
    static VelocityProvider getProvider() {
        return new VelocityProvider() {

            private final Map<String, Integer> mapCard = new HashMap<>();
            private final Map<String, List<Instant>> mapInstant = new HashMap<>();

            @Override
            public int getCardUsageCount(Payment payment, Duration duration) {
                if (mapInstant.get(payment.getHashedCardNumber()) != null) {
                    Instant time = payment.getTimestamp().minus(duration.getSeconds(), ChronoUnit.SECONDS);
                    return (int) mapInstant.get(payment.getHashedCardNumber()).stream()
                            .filter(a -> a.equals(time) || (a.isAfter(time) && a.isBefore(payment.getTimestamp()))).count();
                }
                return 0;
            }

            @Override
            public void registerPayment(Payment payment) {
                if (mapCard.get(payment.getHashedCardNumber()) != null) {
                    mapCard.put(payment.getHashedCardNumber(), mapCard.get(payment.getHashedCardNumber()) + 1);
                } else {
                    mapCard.put(payment.getHashedCardNumber(), 1);
                }
                System.out.println(payment.getTimestamp());
                if (mapInstant.get(payment.getHashedCardNumber()) != null && mapInstant.get(payment.getHashedCardNumber()).size() > 0) {
                    List<Instant> instants = mapInstant.get(payment.getHashedCardNumber());
                    instants.add(payment.getTimestamp());
                } else {
                    List<Instant> list = new ArrayList<>();
                    list.add(payment.getTimestamp());
                    mapInstant.put(payment.getHashedCardNumber(), list);
                }
            }
        };
    }

    public static void main(String args[]) throws Exception {
        final VelocityProvider velocityProvider = VelocityProvider.getProvider();

        try (final Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                final String assoc = scanner.next();
                final String[] split = assoc.split(":");

                final String operation = split[0];

                if (split.length == 3 && "register".equals(operation)) {
                    final long timestamp = Long.parseLong(split[1]);
                    final String hashedCardNumber = split[2];
                    final Payment payment = new Payment(UUID.randomUUID().toString(), Instant.ofEpochMilli(timestamp), hashedCardNumber);

                    velocityProvider.registerPayment(payment);
                } else if (split.length == 4 && "get".equals(operation)) {
                    final long queryTime = Long.parseLong(split[1]);
                    final String hashedCardNumber = split[2];
                    final long durationInSeconds = Long.parseLong(split[3]);
                    System.out.println(velocityProvider.getCardUsageCount(new Payment(UUID.randomUUID().toString(), Instant.ofEpochMilli(queryTime), hashedCardNumber), Duration.ofSeconds(durationInSeconds)));
                } else {
                    throw new RuntimeException("Invalid test input");
                }
            }
        }catch (Exception e){
            throw e;
        }
    }

//    register:1662123600000:c1
//    register:1662123620000:c1
//    register:1662123621000:c2
//    register:1662123630000:c1
//    register:1662123645000:c2
//    get:1662123660000:c1:60
//    get:1662123660000:c1:35
//    get:1662123660000:c1:15
//    get:1662123660000:c3:75
}

