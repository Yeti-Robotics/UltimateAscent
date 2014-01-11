/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yetirobotics.yeti2013;

import com.sun.squawk.platform.posix.natives.Time;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Evan Vitkus
 */
public class Autonomous 
{
    private Shooter shooter;
    private Chamber chamber;
    private Magazine magazine;
    private DriveTrain driveTrain;
    public Autonomous(Shooter shooter, Chamber chamber, Magazine magazine, DriveTrain yetiDrive)
    {
        this.shooter = shooter;
        this.chamber = chamber;
        this.magazine = magazine;
        this.driveTrain = yetiDrive;
    }
            
    public void frontCenter()
    {
        shooter.powerShooter2(.615,.615);//NEED TO CHANGE NUMBERS
        Timer.delay(4);
        //pusherForwardNoLimits()
        while(chamber.forwardSwitch.get() == true)
        {
            chamber.pusherForwardWithLimits();
        }
        while(chamber.backSwitch.get() == true)
        {
            chamber.pusherRetractWithLimits();
        }   
        magazine.unload();
        Timer.delay(0.5);
        magazine.prepare();
        Timer.delay(1);
        //pusherForwardNoLimits()
        while(chamber.forwardSwitch.get() == true)
        {
            chamber.pusherForwardWithLimits();
        }
        while(chamber.backSwitch.get() == true)
        {
            chamber.pusherRetractWithLimits();
        }
        magazine.unload();
        Timer.delay(0.5);
        magazine.prepare();
        Timer.delay(1);
        //pusherForwardNoLimits()
        while(chamber.forwardSwitch.get() == true)
        {
            chamber.pusherForwardWithLimits();
        }
        while(chamber.backSwitch.get() == true)
        {
            chamber.pusherRetractWithLimits();
        }
        magazine.unload();
        shooter.powerShooter2(0,0);
        System.out.println("frontCenter");
    }
    
    public void frontLeft()
    {
        shooter.setSpeeds(.8);//NEED TO CHANGE NUMBERS
        System.out.println("frontLeft");
    }
    
    public void frontRight()
    {
        shooter.setSpeeds(.8);//NEED TO CHANGE NUMBERS
        System.out.println("frontRight");
    }
    
    public void backRightShoot()//Posistion of back right for shooting
    {
        driveTrain.driveEncodersStraight(0.5, 62);
        driveTrain.driveEncodersSpin(0.5, 38.04, false);
        shooter.setSpeeds(.2);
        System.out.println("backRightShoot");
    }
    
    public void backRightDump()//Posistion of back right for dumping
    {
        driveTrain.driveEncodersStraight(0.5, 189);
        shooter.setSpeeds(.2);
        System.out.println("backRightDump");
    }
    
    /*public void backCenter()//Posistion of back center
    {
        driveTrain.driveEncoders(.5,.5, 2000);//NEED TO CHANGE THE NUMBERS
        shooter.setSpeedsIncremental(.5);
        
        System.out.println("backCenter");
    }*/
    
    public void backLeft()
    {
        driveTrain.driveEncodersStraight(0.5, 62);
        driveTrain.driveEncodersSpin(0.5, 38.04, true);
        shooter.setSpeeds(.2);
        System.out.println("backLeft");
    }
    
    public void test()
    { 
        System.out.println("start");
        driveTrain.driveEncodersStraight(0.5, 3);
        shooter.powerShooter(.5, .5);
        Timer.delay(5);                
        //chamber.pusherRetract(); 
        //chamber.pusherForward();
        System.out.println("end");
    }
    
    public void backCenter()
    {
        while(shooter.getSpeed()>41 || shooter.getSpeed()<39)
        {
            shooter.setSpeedRich(40);
        }
        Timer.delay(4);
        while(chamber.forwardSwitch.get() == true)
        {
            chamber.pusherForwardWithLimits();
        }
        while(chamber.backSwitch.get() == true)
        {
            chamber.pusherRetractWithLimits();
        }   
        magazine.unload();
        Timer.delay(0.5);
        magazine.prepare();
        Timer.delay(1);
        while(chamber.forwardSwitch.get() == true)
        {
            chamber.pusherForwardWithLimits();
        }
        while(chamber.backSwitch.get() == true)
        {
            chamber.pusherRetractWithLimits();
        }
        magazine.unload();
        Timer.delay(0.5);
        magazine.prepare();
        Timer.delay(1);
        while(chamber.forwardSwitch.get() == true)
        {
            chamber.pusherForwardWithLimits();
        }
        while(chamber.backSwitch.get() == true)
        {
            chamber.pusherRetractWithLimits();
        }
        magazine.unload();
        shooter.powerShooter2(0,0);
        System.out.println("frontCenter");
    }
    
    public void runAuto(int autoMode) 
    {
        switch(autoMode)
        {
            case 1: frontCenter(); break;
            case 2: frontLeft(); break;
            case 3: frontRight(); break;
            case 4: backRightShoot(); break;
            case 5: backRightDump(); break;
            //case 6: backCenter(); break;
            case 6: backLeft(); break;
            case 7: test(); break;
            case 8: backCenter(); break;
        }
        
    }
    
}

