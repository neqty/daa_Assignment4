package kz.sdu.daa;

public final class Counter {
    private long operations;

    public void tick() {
        operations++;
    }

    public long value() {
        return operations;
    }
}
