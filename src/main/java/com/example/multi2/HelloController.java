package com.example.multi2;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class HelloController {
   @FXML
   TextField txtValue;

   @FXML
   TextField txtX1, txtX2, txtX3, txtInterval1, txtInterval2, txtInterval3;

    @FXML
    TextArea logArea;

    ConcurrentLinkedQueue<String> messages= new ConcurrentLinkedQueue<>();
    LinkedBlockingQueue<String>   messages2=new LinkedBlockingQueue<>();

   SimpleIntegerProperty value = new SimpleIntegerProperty();

   public void initialize()
   {
       value.set(0);
       //txtValue.textProperty().bindBidirectional(value, new DecimalFormat());
       AnimationTimer at = new AnimationTimer() {
           @Override
           public void handle(long l) {
               txtValue.setText(String.valueOf(value.get()));
              /* messages.forEach(s -> logArea.appendText(s));
               messages.clear();*/
               List<String> strings = new LinkedList<>();
               messages2.drainTo(strings);
               strings.forEach(s->logArea.appendText(s));
           }
       };
       at.start();
       value.addListener((v, old_val, new_val)->{
           writeToLog("value changed. was "+old_val+" set "+new_val);
       });

   }

   @FXML
    void start1(){
       int x    = Integer.parseInt(txtX1.getText());
       double t = Double.parseDouble(txtInterval1.getText());
       Worker worker1 = new Worker(x, t, value);

       class InnerThreadClass implements Runnable{

           @Override
           public void run() {
               try {
                   writeToLog("worker1 started");
                   worker1.work();
                   writeToLog("worker1 finished");
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
       }
       Thread workThread = new Thread(new InnerThreadClass());

       workThread.start();
   }

    @FXML
    void start2(){
        int x    = Integer.parseInt(txtX2.getText());
        double t = Double.parseDouble(txtInterval2.getText());
        Worker worker2 = new Worker(x, t, value);
        Thread workThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    writeToLog("worker2 started");
                    worker2.work();
                    writeToLog("worker2 finished");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        workThread.start();
    }
    @FXML
    void start3(){
        int x    = Integer.parseInt(txtX3.getText());
        double t = Double.parseDouble(txtInterval3.getText());
        Worker worker3 = new Worker(x, t, value);
        Thread workThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    writeToLog("worker3 started");
                    worker3.work();
                    writeToLog("worker3 finished");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        workThread.start();
    }

    public void writeToLog(String message)
    {
        System.out.println(message);
        //logArea.appendText(message+"\n");
        messages.add(message+"\n");
        messages2.add(message+"\n");
    }
}