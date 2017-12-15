package com.ariescc.rmi.client;

public class RMIClient {

    public static void main(String[] args) {

        if(args.length < 4) {
            System.out.println("Usage: Command don't exist.");
            System.exit(1);
        }

        if(args[3].equals(Method.register)) {
            if(args.length != 7) {
                System.out.println("Usage: java [clientName] [serverName] [portNumber] register " +
                        "[userName] [password]");
                System.exit(1);
            }

            // 调用 Register 方法
            handleRegister(args);

        } else if (args[3].equals(Method.add)) {
            if(args.length != 11) {
                System.out.println("Usage: java [clientName] [serverName] [portNumber] add" +
                        " [username] [password] [otherUsername] [start] [end] [title]");
                System.exit(1);
            }
            System.out.println("Successful!");
            // 调用 Add 方法
            handleAdd(args);

        } else if (args[3].equals(Method.query)) {
            if(args.length != 9) {
                System.out.println("Usage: java [clientName] [serverName] [portNumber] query " +
                        "[username] [password] [start] [end]");
                System.exit(1);
            }
            System.out.println("Successful!");
            // 调用 Query 方法
            handleQuery(args);

        } else if (args[3].equals(Method.delete)) {
            if(args.length != 8) {
                System.out.println("Usage: java [clientName] [serverName] [portNumber] delete " +
                        "[username] [password] [meetingId]");
                System.exit(1);
            }
            System.out.println("Successful!");
            // 调用 Delete 方法
            handleDelete(args);

        } else if (args[3].equals(Method.clear)) {
            if(args.length != 7) {
                System.out.println("Usage: java [clientName] [serverName] [portNumber] clear " +
                        "[username] [password]");
                System.exit(1);
            }
            System.out.println("Successful!");
            // 调用 Clear 方法
            handleClear(args);

        }

    }

    /**
     * 处理 Clear
     * @param args
     */
    private static void handleClear(String[] args) {
    }

    /**
     * 处理 Delete
     * @param args
     */
    private static void handleDelete(String[] args) {
    }

    /**
     * 处理 Query
     * @param args
     */
    private static void handleQuery(String[] args) {
    }

    /**
     * 处理 Add
     * @param args
     */
    private static void handleAdd(String[] args) {
    }

    /**
     * 处理 Register
     * @param args
     */
    private static void handleRegister(String[] args) {

        // 解析命令


    }

}
