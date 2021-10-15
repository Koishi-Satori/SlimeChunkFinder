public class timer {
    long start;
    public void start() {
        start = System.currentTimeMillis();
    }
    public long end(long start) {
        long end = System.currentTimeMillis();
        return end -start;
    }
}
