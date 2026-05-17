// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.PriorityQueue;

public class ClientHandler implements Runnable {
   private final Socket clientSocket;
   private final PriorityQueue<Notification> queue;
   private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

   public ClientHandler(Socket var1, PriorityQueue<Notification> var2) {
      this.clientSocket = var1;
      this.queue = var2;
   }

   public void run() {
      try {
         BufferedReader var1 = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

         try {
            BufferedWriter var2 = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));

            String var3;
            try {
               while((var3 = var1.readLine()) != null) {
                  try {
                     String[] var4 = var3.split("\\|", 2);
                     if (var4.length < 2) {
                        throw new IllegalArgumentException("Invalid data format");
                     }

                     LocalDateTime var5 = LocalDateTime.parse(var4[0], FORMATTER);
                     String var6 = var4[1];
                     synchronized(this.queue) {
                        this.queue.add(new Notification(var5, var6, var2));
                     }

                     System.out.println("[SAVED] Notification for " + var4[0] + " from " + String.valueOf(this.clientSocket.getInetAddress()));
                  } catch (IllegalArgumentException | DateTimeParseException var26) {
                     System.err.println("[ERROR] Invalid data from client: " + ((RuntimeException)var26).getMessage());
                  }
               }
            } catch (Throwable var27) {
               try {
                  var2.close();
               } catch (Throwable var24) {
                  var27.addSuppressed(var24);
               }

               throw var27;
            }

            var2.close();
         } catch (Throwable var28) {
            try {
               var1.close();
            } catch (Throwable var23) {
               var28.addSuppressed(var23);
            }

            throw var28;
         }

         var1.close();
      } catch (IOException var29) {
         System.out.println("[DISCONNECT] Connection with client " + String.valueOf(this.clientSocket.getInetAddress()) + " lost.");
      } finally {
         try {
            this.clientSocket.close();
         } catch (IOException var22) {
            System.err.println("[ERROR] Failed to close socket.");
         }

      }

   }
}
