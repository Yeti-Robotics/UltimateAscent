/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yetirobotics.yeti2013;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Timer;



/**
 * Rooty-Tooty Point'n'shooty
 * 
 * RRRRRRR  OOOOOOOO OOOOOOOO TTTTTTTT YY    YY     TTTTTTTT OOOOOOOO OOOOOOOO TTTTTTTT YY    YY         
 * RR   RRR OO    OO OO    OO    TT     YY  YY         TT    OO    OO OO    OO    TT     YY  YY          
 * RRRRRRR  OO    OO OO    OO    TT      YYYY   ---    TT    OO    OO OO    OO    TT      YYYY           
 * RR   RRR OO    OO OO    OO    TT       YY    ---    TT    OO    OO OO    OO    TT       YY            
 * RR    RR OOOOOOOO OOOOOOOO    TT       YY           TT    OOOOOOOO OOOOOOOO    TT       YY            
 * 
 * PPPPPPPP OOOOOOOO IIIIIIII N     N TTTTTTTT '' N     N '' SSSSSSSS HH    HH OOOOOOOO OOOOOOOO TTTTTTTT YY    YY
 * PP    PP OO    OO    II    N N   N    TT    '' N N   N '' SS       HH    HH OO    OO OO    OO    TT     YY  YY
 * PPPPPPPP OO    OO    II    N  N  N    TT    '' N  N  N '' SSSSSSSS HHHHHHHH OO    OO OO    OO    TT      YYYY
 * PP       OO    OO    II    N   N N    TT       N   N N          SS HH    HH OO    OO OO    OO    TT       YY
 * PP       OOOOOOOO IIIIIIII N    NN    TT       N    NN    SSSSSSSS HH    HH OOOOOOOO OOOOOOOO    TT       YY
 * 
 * @author pureFloat
 */
public class Shooter {
    Jaguar frontJag;
    Jaguar backJag;
    Encoder frontEncoder;
    Encoder backEncoder;
    DoubleSolenoid shooterPiston;
    int shootAngle = 1;
    int previousShootAngle = 1;
    Counter hallSensorFrontMotor;
    Counter hallSensorBackMotor;
    int front = 0;
    int back = 1;
    double realSpeed;
    double speedDifference;
    double speedRatio;
    double realPower;
    double currentSpeed;
    double increaseSpeed;


    public Shooter(int frontJagPos, int backJagPos,/* int frontEncoderAPos, int frontEncoderBPos, int backEncoderAPos, int backEncoderBPos,*/ /*int shooterPistonForwardPos, int shooterPistonReversePos,*/ int hallSensorFrontMotorPos, int hallSensorBackMotorPos) {
        frontJag = new Jaguar(frontJagPos);
        backJag = new Jaguar(backJagPos);
        /*frontEncoder = new Encoder(frontEncoderAPos, frontEncoderBPos);
        backEncoder = new Encoder(backEncoderAPos, backEncoderBPos);*/
        //shooterPiston = new DoubleSolenoid(shooterPistonForwardPos, shooterPistonReversePos);
        hallSensorFrontMotor = new Counter(new DigitalInput(hallSensorFrontMotorPos));
        hallSensorBackMotor = new Counter(new DigitalInput(hallSensorBackMotorPos));
        hallSensorFrontMotor.reset();
        hallSensorBackMotor.reset();
        hallSensorFrontMotor.start();
        hallSensorBackMotor.start();
    }
    
   public void setSpeedComplete(double targetSpeed, double threshold)
   {
       currentSpeed = frontJag.get();
       speedDifference = targetSpeed - getSpeed();
       increaseSpeed =  speedDifference/targetSpeed*.1;
       if(Double.isNaN(hallSensorFrontMotor.getPeriod()))
       {
           currentSpeed+=.0035; 
           frontJag.set(currentSpeed);
           backJag.set(currentSpeed);
       }
       else
       {
            if (targetSpeed+threshold > getSpeed())
            {
                currentSpeed+=increaseSpeed; 
                frontJag.set(currentSpeed);
                backJag.set(currentSpeed);
                //frontJag.set(frontJag.get()+.0035);
                //backJag.set(frontJag.get());
            }
            else if(targetSpeed-threshold < getSpeed()/* || Double.isNaN(hallSensorFrontMotor.getPeriod())*/)
            {
                currentSpeed+=increaseSpeed; 
                frontJag.set(currentSpeed);
                backJag.set(currentSpeed);
                //frontJag.set(frontJag.get()-.0025);
                //backJag.set(frontJag.get());
            }
       }
   }
    
    public void setSpeedsExponential(double targetSpeedFront/*, double frontSpeed, double backSpeed*/)
    {
       /* double frontDifference = getFrontDifference(targetSpeedFront);
        if(targetSpeedFront != getSpeed(front))
        {
            //frontJag.set(frontJag.get()/frontSpeed);
            frontJag.set(getSpeed(front)+(frontDifference/2));
        }  
        double backDifference = getBackDifference(targetSpeedBack);
        if(targetSpeedBack != getSpeed(back))
        {
            //frontJag.set(frontJag.get()/frontSpeed);
            frontJag.set(getSpeed(back)+(backDifference/2));
        }*/  
    }
    
   public double getSpeed(/*int wheel*/)
    {
        realSpeed = 1/hallSensorFrontMotor.getPeriod();
        return realSpeed;
       /* if(wheel == 0)
        {
            double currentSpeedFront = frontJag.get();
            return currentSpeedFront;
        }
        else
        {
            double currentSpeedBack = backJag.get();
            return currentSpeedBack;
        }*/
    }
   
   public double getPower(/*int wheel*/)
    {
        realPower = frontJag.get();
        return realPower;
       /* if(wheel == 0)
        {
            double currentSpeedFront = frontJag.get();
            return currentSpeedFront;
        }
        else
        {
            double currentSpeedBack = backJag.get();
            return currentSpeedBack;
        }*/
    }
   
   public void setSpeedRich(double targetSpeed)
   {
       currentSpeed = frontJag.get();
       if (targetSpeed > getSpeed() || Double.isNaN(hallSensorFrontMotor.getPeriod()))
       {   
           currentSpeed+=.0035; 
           frontJag.set(currentSpeed);
           backJag.set(currentSpeed);
           //frontJag.set(frontJag.get()+.0035);
           //backJag.set(frontJag.get());
       }
       else if(targetSpeed < getSpeed() || Double.isNaN(hallSensorFrontMotor.getPeriod()))
       {
           currentSpeed-=.0025; 
           frontJag.set(currentSpeed);
           backJag.set(currentSpeed);
           //frontJag.set(frontJag.get()-.0025);
           //backJag.set(frontJag.get());
       }
   }
   
    public void setSpeeds(double frontMotor){
        speedDifference = (frontMotor)*60 - realSpeed;
        //System.out.println(realSpeed/frontJag.get());
        System.out.println("hall speed" + getSpeed());
        System.out.println("hall period " + hallSensorFrontMotor.getPeriod());
        if(Double.isNaN(hallSensorFrontMotor.getPeriod())){
            frontJag.set(frontMotor);
            backJag.set(frontMotor);
            }
        else {
            if (speedDifference > 1){
                frontJag.set(frontJag.get() + .01);
                backJag.set(backJag.get() + .01);
            } 
            else if (speedDifference < 1){
                frontJag.set(frontJag.get() - .01);
                backJag.set(backJag.get() - .01);
            }
        }
    }
    
    /*public double getFrontDifference(double targetSpeedFront)
    {
        double frontDifference = getSpeed(front)-targetSpeedFront;
        return frontDifference; 
    }
    
    public double getBackDifference(double targetSpeedBack)
    {
        double backDifference = getSpeed(back)-targetSpeedBack;
        return backDifference; 
    }*/
    public void powerShooter(double frontMotor, double backMotor)
    {
        frontJag.set(frontMotor);
        backJag.set(backMotor);
    }
    
    public void powerShooter2(double frontMotor, double backMotor)
    {
        while(frontJag.get()<frontMotor && backJag.get()<backMotor)
        {
            if(frontJag.get()<frontMotor)
            {
                frontJag.set(frontJag.get()+.01);            
            }
            if(backJag.get()<backMotor)
            {
                backJag.set(backJag.get()+.01);            
            }
            Timer.delay(.01);
        }
    }
    
}

