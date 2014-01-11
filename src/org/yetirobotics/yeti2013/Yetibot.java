/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//#420YOLOSWAG
package org.yetirobotics.yeti2013;


import edu.wpi.first.wpilibj.*;




/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Yetibot extends IterativeRobot {
    
    //DRIVER STATION
    public static int LEFT_JOY_POS = 2;  //Driver Station
    public static int RIGHT_JOY_POS = 1;  //Driver Station
    public static int SHOOT_JOY_POS = 3;  //Driver Station
    
    //DRIVE TRAIN
    
    public static int LEFT_JAG1_POS = 4;//DIGSC
    public static int LEFT_JAG2_POS = 3;//DIGSC
    public static int RIGHT_JAG1_POS = 1;//DIGSC
    public static int RIGHT_JAG2_POS = 2;//DIGSC
    public static int LEFT_ENCODER_A_POS = 4;//Di
    public static int LEFT_ENCODER_B_POS = 5;//DI
    public static int RIGHT_ENCODER_A_POS = 2;//DI
    public static int RIGHT_ENCODER_B_POS = 3;//DI
    public static int SHIFTER_FORWARD_POS = 7;//SOLENOID BREAKOUT
    public static int SHIFTER_REVERSE_POS = 8;//SOLENOID BREAKOUT
    
    //SHOOTER
    public static int FRONT_JAG_POS = 5;//DIGSC
    public static int BACK_JAG_POS = 6;//DIGSC
    public static int HALL_SENSOR_FRONT_MOTOR = 6; //DI
    public static int HALL_SENSOR_BACK_MOTOR = 7;//DI
    
    //aircompressor is in 1 RO
    //pressure switch is in 1 DI
    
    //CHAMBER
    
    public static int PUSHER_POS = 6;//RO
    public static int FORWARD_LIMIT_POS = 9;//DI
    public static int BACK_LIMIT_POS = 10;//DI fix dis filthy shit

    
    //CLIMBER
    
    public static int WINCH_SPIKE_POS = 3;//RO SPIKE
    public static int WINCH_SWITCH_POS = 8;//DI
    public static int CLIMBER_PISTON_POS_1 = 3;//SOLENOID BREAKOUT
    public static int CLIMBER_PISTON_POS_2 = 4;//SOLENOID BREAKOUT 
    //public static int MOVE_SWITCH_POS = 8;
    public static int LIFTER_UP_SENSOR_POS = 11;
    public static int LIFTER_DOWN_SENS0R__POS = 8;
    
    //MAGAZINE
    
    public static int STOPPER_PISTON_POS1 = 1;//SOLENOID BREAKOUT
    public static int STOPPER_PISTON_POS2 = 2;//SOLENOID BREAKOUT
    //public static int LOADED_SWITCH_POS = 7;//NEEDS TO BE CHANGED.  RANDOM NUMBER HERE!!!!!!!!!!!!!!!!
    
    //TRACKER
    //public static int PAN_SERVO_POS = 7;
    //public static int TILT_SERVO_POS = 8;
    //public static int CAMERA_POS = 3;
    public static int LIGHT_POS = 5;//RO
    //public static int SONAR_POS = 5;
    
    //FUN COMPRESSOR STUFF
    
    public static int COMPRESSOR_RELAY_POS = 1; //RO
    public static int DIGITAL_COMPRESSOR_POS = 1;//di
    
    //AUTONOMOUS
    
    public static int AUTO_MODE = 1;
    
    //YETIBOT
    
    public static int SONAR_POS = 1;
    
    Joystick leftJoy;
    Joystick rightJoy;
    Joystick shootJoy;
    AnalogChannel sonar;
    Timer timer;
    
    //Servo yawServo = new Servo(YAW_SERVO_POS);
    //Servo panServo = new Servo(PAN_SERVO_POS);
    DriverStation driverStation;
    DriverStationLCD driverStationLCD;
    
    Shooter shooter;
    DriveTrain yetiDrive;
    Tracker tracker;
    Chamber chamber;
    Magazine magazine;
    Climber climber;
    Autonomous autonomous;
    boolean button3PrevState = false;
    boolean button3CurrState = false;
    Relay light = new Relay(LIGHT_POS);
    double shootSpeed;
    boolean hasRun;
    KinectStick leftArm;
    KinectStick rightArm;
    double speed;
    
    
    private Relay compressorSpike = new Relay(COMPRESSOR_RELAY_POS);
    private DigitalInput digitalCompressor = new DigitalInput(DIGITAL_COMPRESSOR_POS);
    
    public void robotInit() {
        System.out.println("Is this working?");
        leftJoy = new Joystick(LEFT_JOY_POS);
        rightJoy = new Joystick(RIGHT_JOY_POS);
        shootJoy = new Joystick(SHOOT_JOY_POS);
        shooter = new Shooter (FRONT_JAG_POS, BACK_JAG_POS, HALL_SENSOR_FRONT_MOTOR, HALL_SENSOR_BACK_MOTOR);
        yetiDrive = new DriveTrain(LEFT_JAG1_POS, LEFT_JAG2_POS, RIGHT_JAG1_POS, RIGHT_JAG2_POS, SHIFTER_FORWARD_POS, SHIFTER_REVERSE_POS, LEFT_ENCODER_A_POS, LEFT_ENCODER_B_POS, RIGHT_ENCODER_A_POS, RIGHT_ENCODER_B_POS);
        //tracker = new Tracker (TILT_SERVO_POS, PAN_SERVO_POS, yetiDrive);
        chamber = new Chamber(PUSHER_POS, FORWARD_LIMIT_POS,BACK_LIMIT_POS);
        magazine = new Magazine(STOPPER_PISTON_POS1, STOPPER_PISTON_POS2/*, LOADED_SWITCH_POS*/);
        climber = new Climber(CLIMBER_PISTON_POS_1, CLIMBER_PISTON_POS_2);
        autonomous = new Autonomous(shooter, chamber, magazine, yetiDrive);
        sonar = new AnalogChannel(SONAR_POS);
        timer = new Timer();
        hasRun = false;
        speed = 0;
        
        leftArm = new KinectStick(1);
        rightArm = new KinectStick(2);
        driverStation = DriverStation.getInstance();
        driverStationLCD = DriverStationLCD.getInstance();
        
    }
    
    public void teleopInit() {
        System.out.println("teleopInit");
        shooter.powerShooter2(0, 0);
        
    }

    public void autonomousPeriodic()
    {
        if(!hasRun)
        {
            autonomous.runAuto(AUTO_MODE);
            hasRun = true;
        }
    }
    

    public void teleopPeriodic() {
        hasRun = false;
        //shootSpeed = -(shootSpeed/2.0) + 0.5;
        //frontShootJag.set(shootSpeed);
        if(shootJoy.getRawButton(9))
        {
            autonomous.backCenter();
        }
        if(shootJoy.getRawButton(5))
        {
            shooter.setSpeedComplete(40,2);
            /*driverStationLCD.println(DriverStationLCD.Line.kUser2, 1, "Shooter Speed: " + shooter.getSpeed());
            driverStationLCD.println(DriverStationLCD.Line.kUser3, 1, "Shooter Power: " + shooter.getPower());
            driverStationLCD.updateLCD();*/
            Timer.delay(.1);
        }
        else
        {
            if(leftArm.getRawButton(7))
            {
                speed += .0001;
            }
            else if(leftArm.getRawButton(8))
            {
                speed -= .0001;
            }
            
            if(speed > 1)
            {
                speed = 1;
            }
            else if(speed < 0)
            {
                speed = 0;
            }
            //double frontSpeed = (shootJoy.getZ() + 1) / 2.0;
            //double backSpeed = (shootJoy.getZ() + 1) / 2.0;
            shooter.powerShooter(speed, speed);
        }
        //shootSpeed = shootJoy.getZ();
        //shootSpeed = (shootSpeed + 1.0)*10;
        //System.out.println("target Speed" + shootSpeed);
        
        if(rightJoy.getRawButton(2))
        {
            System.out.println("button 2 pressed");
            climber.climbUp();
        }
        else if(rightJoy.getRawButton(6))
        {
            System.out.println("button 6 pressed");
            climber.climbDown();
        }
        /*else
        {
            climber.stop();
        }*/
        if(rightJoy.getTrigger())
        {
            yetiDrive.shiftUp();
        }
        else
        {
            yetiDrive.shiftDown();
        }
        //if(shootJoy.getRawButton(4))
        if(leftArm.getRawButton(1))
        {
            chamber.pusherForwardWithLimits();
        }
        else
        {
           chamber.pusherRetractWithLimits();
        }
        
        
        /*
        else if(shootJoy.getRawButton(5))
        {
           chamber.pusherRetractWithLimits();
        }
        else if(shootJoy.getRawButton(11))
        {
            chamber.pusherRetractNoLimits();
        }
        else if(shootJoy.getRawButton(10))
        {
            chamber.pusherForwardNoLimits();
        }
        else
        {
            chamber.pusherStop();
        }*/
        while(shootJoy.getRawButton(6))
        {
            double yAxis = shootJoy.getY()/2.0;
            double xAxis = shootJoy.getX()/2.0;
            yetiDrive.drive(yAxis+xAxis, yAxis-xAxis);
        }
        //if(shootJoy.getRawButton(7))
        if(leftArm.getRawButton(2))
        {
            magazine.prepare();
        }
        else
        {
            magazine.unload();
        }
        //shooter.setSpeedsIncremental(shootSpeed);
        
        /*if(leftJoy.getRawButton(12))
        {
            yawServo.set(yawServo.get()+2);
        }
        if(leftJoy.getRawButton(11)){
            yawServo.set(yawServo.get()-2);
        }
        if(leftJoy.getRawButton(10)){
            panServo.set(panServo.get()-2);
        }
        if(leftJoy.getRawButton(9)){
            panServo.set(panServo.get()+2);
        }*/
        //yetiDrive.drive(leftJoy.getY(), rightJoy.getY());
        yetiDrive.drive(leftArm.getY(), rightArm.getY());
        
        /*button3PrevState = button3CurrState;
        button3CurrState = shootJoy.getRawButton(3);
        if(button3PrevState==true && button3CurrState==false)
        {
            //shooter.toggleAngle();
        }*/
        //System.out.println(leftJoy.getRawButton(2));
        //int[] targetCoords = null;
        //System.out.println("juguitug");
        //driverStationLCD.println(DriverStationLCD.Line.kUser2, 1, "Battery Voltage: " + driverStation.getBatteryVoltage());
        driverStationLCD.println(DriverStationLCD.Line.kUser2, 1, "Shooter Speed: " + shooter.getSpeed());
        driverStationLCD.println(DriverStationLCD.Line.kUser3, 1, "Shooter Power: " + shooter.getPower());
        driverStationLCD.updateLCD();
        
        while(shootJoy.getRawButton(3))
        {
            light.set(Relay.Value.kForward);
            System.out.println("\n" + "tracking high");
            tracker.trackTarget("HIGH");
            driverStationLCD.println(DriverStationLCD.Line.kUser1, 1, "Tracking high");
            driverStationLCD.updateLCD();
            timer.delay(.005);
        }
        while(shootJoy.getRawButton(2))
        {
            light.set(Relay.Value.kForward);
            System.out.println("\n" + "tracking mid");
            tracker.trackTarget("MID");
            driverStationLCD.println(DriverStationLCD.Line.kUser1, 1, "Tracking mid ");
            driverStationLCD.updateLCD();
            timer.delay(.005);
        }
        light.set(Relay.Value.kOff);
        /*else
        {
            System.out.println("\n" + "not tracking");
            tracker.trackTarget("OTHER");
        }*/
        
        if (!digitalCompressor.get())
        {
            compressorSpike.set(Relay.Value.kForward);
            //System.out.println("Not Full");
        }
        else
        {
             compressorSpike.set(Relay.Value.kOff);
             //System.out.println("Full");
        }
        //System.out.println("teleopPeriodic");
        //shooter.setSpeedsIncremental(leftJoy.getThrottle(), autoMode);
    }
    
    
    public void testPeriodic() {
    System.out.println("testPeriodic");
    }
    
}
