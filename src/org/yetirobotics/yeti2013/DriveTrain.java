/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yetirobotics.yeti2013;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Timer;
import com.sun.squawk.microedition.io.FileConnection;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import javax.microedition.io.Connector;

/**
 * Rolliepoly
 * 
 * RRRRRRR  OOOOOOOO LL     LL     IIIIII EEEEEEEE PPPPPPP  OOOOOOOO LL    YY    YY
 * RR   RRR OO    OO LL     LL       II   EE       PP   PPP OO    OO LL     YY  YY
 * RRRRRRR  OO    OO LL     LL       II   EEEEEE   PPPPPPP  OO    OO LL      YYYY
 * RR   RRR OO    OO LL     LL       II   EE       PP       OO    OO LL       YY
 * RR    RR OOOOOOOO LLLLLL LLLLLL IIIIII EEEEEEEE PP       OOOOOOOO LLLLLL   YY
 * 
 * @author pureFloat
 */
public class DriveTrain {
    Jaguar leftJag1;
    Jaguar leftJag2;
    Jaguar rightJag1;
    Jaguar rightJag2;
    
    final double upShiftSpeed = 2.5;
    final double downShiftSpeed = .5;
    double currentSpeed = 0;
    
    DoubleSolenoid shifter;
    
    Encoder leftEncoder;
    Encoder rightEncoder;
    
    boolean automatic = true;
    
    Timer timer;

    private DataOutputStream logFile;
    private FileConnection fc;

    boolean shiftedUp = false;
    
    int leftJag1Pos = Yetibot.LEFT_JAG1_POS;
    int leftJag2Pos = Yetibot.LEFT_JAG2_POS;
    int rightJag1Pos = Yetibot.RIGHT_JAG1_POS;
    int rightJag2Pos = Yetibot.RIGHT_JAG1_POS;
    

    
    public DriveTrain(int leftJag1Pos, int leftJag2Pos, int rightJag1Pos, int rightJag2Pos, 
            int shifterForwardPos, int shifterReversePos, int leftEncoderAPos, int leftEncoderBPos, int rightEncoderAPos, int rightEncoderBPos) {
        
        leftJag1 = new Jaguar(leftJag1Pos);
        leftJag2 = new Jaguar(leftJag2Pos);
        rightJag1 = new Jaguar(rightJag1Pos);
        rightJag2 = new Jaguar(rightJag2Pos);
        
        shifter = new DoubleSolenoid(shifterForwardPos, shifterReversePos);
        
        leftEncoder = new Encoder(leftEncoderAPos, leftEncoderBPos, false, CounterBase.EncodingType.k4X);
        rightEncoder = new Encoder(rightEncoderAPos, rightEncoderBPos, true, CounterBase.EncodingType.k4X);
        leftEncoder.setDistancePerPulse(0.00290888); // assuming 360 pulses per revolution and 4" diameter
        rightEncoder.setDistancePerPulse(0.00290888); // assuming 360 pulses per revolution and 4" diameter
        leftEncoder.start();
        rightEncoder.start();
        leftEncoder.reset();
        rightEncoder.reset();
        
        timer = new Timer();
        timer.start();

        try {
          fc = (FileConnection)Connector.open("file:///log.csv", Connector.WRITE);
          fc.create();
          logFile = fc.openDataOutputStream();
        } 
        catch (Exception e) {
          System.out.println("File open error.");
        }        
    }
    
    public void drive(double leftValue, double rightValue) {
        leftJag1.set(-leftValue);
        leftJag2.set(-leftValue);
        rightJag1.set(rightValue);
        rightJag2.set(rightValue);
        //System.out.println("left " + leftEncoder.getDistance());
        //System.out.println("right " + rightEncoder.getDistance());
        /*if(automatic)
        {
            getSpeed();
            
        }*/
        /*try {
          logFile.writeChars(leftValue + "," + rightValue);
          logFile.writeChars("," + leftEncoder.getRate() + "," + rightEncoder.getRate());
          logFile.writeChars("\n");
          logFile.flush();
        } 
        catch (Exception e) {
          System.out.println("File write error.");
        }    */    
    }
    
    public void driveEncodersStraight(double power, double distance) {
        leftEncoder.reset();
        rightEncoder.reset();
        while(/*distance > leftEncoder.getDistance() && */distance > Math.abs(rightEncoder.getDistance()))
        {    
            /*if(distance > leftEncoder.getDistance())
            {
                leftJag1.set(-power);
                leftJag2.set(-power);
            }
            else
            {
                leftJag1.set(0);
                leftJag2.set(0);
            }*/
            if(distance > Math.abs(rightEncoder.getDistance()))
            {
                leftJag1.set(power);
                leftJag2.set(power);
                rightJag1.set(-power);
                rightJag2.set(-power);
                //System.out.println(Math.abs(rightEncoder.getDistance()));
            }
            else
            {
                leftJag1.set(0);
                leftJag2.set(0);
                rightJag1.set(0);
                rightJag2.set(0);
            }
        }
        leftJag1.set(0);
        leftJag2.set(0);
        rightJag1.set(0);
        rightJag2.set(0);
    }
    
    public void driveEncodersSpin(double power, double arcDistance, boolean clockwise) {
        leftEncoder.reset();
        rightEncoder.reset();
        double rightPower;
        double leftPower;
        if(clockwise)
        {
            rightPower = -power;
            leftPower = -power;
        }
        else
        {
            rightPower = power;
            leftPower = power;
        }
        
        while(arcDistance > leftEncoder.getDistance() && arcDistance > rightEncoder.getDistance())
        {    
            if(arcDistance > leftEncoder.getDistance())
            {
                leftJag1.set(-leftPower);
                leftJag2.set(-leftPower);
            }
            else
            {
                leftJag1.set(0);
                leftJag2.set(0);
            }
            if(arcDistance > rightEncoder.getDistance())
            {
                rightJag1.set(rightPower);
                rightJag2.set(rightPower);
            }
            else
            {
                rightJag1.set(0);
                rightJag2.set(0);
            }
        }
        leftJag1.set(0);
        leftJag2.set(0);
        rightJag1.set(0);
        rightJag2.set(0);
    }
    
    public void setSpeed() {
        
    }
    
    public double getSpeed() {
        
        //double elapsedTime = timer.get() * (.000001);
        
        //speed of slowest wheel (shifts if both are above shiftspeed)
        //currentSpeed = (leftEncoder.getRate()>rightEncoder.getRate()) ? rightEncoder.getRate() : leftEncoder.getRate();
        
        //speed of fastest wheel (shifts if one is above shiftspeed)
        //currentSpeed = (leftEncoder.getRate()<rightEncoder.getRate()) ? rightEncoder.getRate() : leftEncoder.getRate();
        
        //average speed of wheels
        currentSpeed = Math.abs(rightEncoder.getRate()); //(leftEncoder.getRate() + rightEncoder.getRate())/2;
        
        //double distTraveled = (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2;
        //currentSpeed = distTraveled / elapsedTime; // fps
        
       
        //leftEncoder.reset();
        //rightEncoder.reset();
        //timer.reset();
       
        if (automatic){
            if (currentSpeed > upShiftSpeed) {
            shiftUp();
            }
            else if (currentSpeed < downShiftSpeed){
            shiftDown();
            }
        }
    
        return currentSpeed;  
    }
    
  /*  public void getGear ()
    {
       int gear;
       gear = shifter.Value();
       if(shifter.get() == kForward_val.Value)
       {
           shifter.get(DoubleSolenoid
    }*/
    
     public void shiftUp() {
            shifter.set(DoubleSolenoid.Value.kForward);
        
        }
     public void shiftDown(){
            shifter.set(DoubleSolenoid.Value.kReverse);
        }
     
    public void setTransmission(boolean isAuto) {
        automatic = isAuto;
    } 
    public void getGear(){
        if(shifter.get() == (DoubleSolenoid.Value.kForward)) {
            shiftedUp = true;
        }
        else{
            shiftedUp = false;
        }
    }
        
    
    
    public boolean getTransmission() {
        return automatic;
    }
    
    public void toggleTransmission(){
        automatic = !automatic;
    }
}
