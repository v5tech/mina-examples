package net.aimeizi.mina.examples.handler;

public class Command {
    public static final int LOGIN = 0;

    public static final int QUIT = 1;

    public static final int BROADCAST = 2;

    private final int num;

    private Command(int num) {
        this.num = num;
    }

    public int toInt() {
        return num;
    }

    public static Command valueOf(String s) {
        s = s.toUpperCase();
        if ("LOGIN".equals(s)) {
            return new Command(LOGIN);
        }
        if ("QUIT".equals(s)) {
            return new Command(QUIT);
        }
        if ("BROADCAST".equals(s)) {
            return new Command(BROADCAST);
        }

        throw new IllegalArgumentException("Unrecognized command: " + s);
    }
}
