// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.operation;

import java.util.ArrayList;
import java.util.Iterator;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is the default message report handler class. Reported message will be
 * sent to sysout, and will be displayed on SmartdashBoard
 */
public class ReportSender implements ReportHandler {
    ArrayList<ReportItem> reportItemList = new ArrayList<ReportItem>();
    double timeCurrent = 0;

    @Override
    public void reportMessage(Operation operation, ReportType type, String message) {
        ReportItem item = new ReportItem();
        item.operation = operation;
        item.type = type;
        item.message = message;
        item.timeStamp = timeCurrent;

        System.out.println(item.toString());
        reportItemList.add(item);
    }

    @Override
    public void updateReport(Context context) {
        timeCurrent = context.getTimeTotal();

        ArrayList<String> messageArrayList = new ArrayList<String>();
        ArrayList<String> warningArrayList = new ArrayList<String>();
        ArrayList<String> errorArrayList = new ArrayList<String>();

        ArrayList<ReportItem> tempList = (ArrayList<ReportItem>) reportItemList.clone();
        Iterator<ReportItem> iter = tempList.iterator();
        while (iter.hasNext()) {
            ReportItem item = iter.next();
            if (timeCurrent - item.timeStamp > 10) {
                // delete old message
                this.reportItemList.remove(item);
            } else {
                // add to a category list
                switch (item.type) {
                    case MESSAGE:
                        messageArrayList.add(item.toString());
                        break;

                    case WARNING:
                        warningArrayList.add(item.toString());
                        break;

                    case ERROR:
                        errorArrayList.add(item.toString());
                        break;

                    default:
                        break;
                }
            }
        }

        String[] messageStringArray = new String[messageArrayList.size()];
        messageArrayList.toArray(messageStringArray);
        String[] warningStringArray = new String[warningArrayList.size()];
        messageArrayList.toArray(messageStringArray);
        String[] errorStringArray = new String[errorArrayList.size()];
        messageArrayList.toArray(messageStringArray);

        SmartDashboard.putStringArray("Op Messages", messageStringArray);
        SmartDashboard.putStringArray("Op Warning", warningStringArray);
        SmartDashboard.putStringArray("Op Error", errorStringArray);
    }
}

/**
 * struct to store a report
 */
class ReportItem {
    double timeStamp;
    Operation operation;
    ReportType type;
    String message;

    @Override
    public String toString() {
        int min;
        double sec;
        min = (int) (timeStamp / 60.0);
        sec = timeStamp % 60;

        String typeStr = type.name();
        String operationName = operation.getClass().getName();

        String out = String.format("[%02d:%02f][%s]%s: %s", min, sec, typeStr, operationName, message);
        return out;
    }
}
