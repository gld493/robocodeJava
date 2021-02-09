package AG;

import robocode.*;
import robocode.util.Utils;

import java.awt.Color;

public class AGRobot extends Robot {

    int direction = 1;
    //double bearingToEnemy;

    @Override
    public void run() {

        setColors(Color.BLUE, Color.YELLOW, Color.RED);
        setAdjustRadarForRobotTurn(true); // unlock the radar from body
        setAdjustGunForRobotTurn(true); // unlock the gun from body
        setAdjustRadarForGunTurn(true); // unlock the radar from gun

        while (true) {      // game loop
            turnRadarRight(360); // spin radar

            ahead(100 * direction);
            //turnGunRight(360);
            //back(100);
            //turnGunRight(360);

        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        double enemyAbsoluteBearingDegrees = e.getBearing() + getHeading();

        //bearingToEnemy = e.getBearing();
        turnRight(e.getBearing() + 90); // face the enemy

        // gun head on targeting
        double gunTurnDegrees = enemyAbsoluteBearingDegrees - getGunHeading();
        turnGunRight(Utils.normalRelativeAngleDegrees(gunTurnDegrees));

        if (getGunHeat() == 0) {
            fire(e.getDistance() > 200 ? 2.4 : 3.0);
        }

        ahead(100 * direction);

        // radar lock
        double radarTurnDegrees = enemyAbsoluteBearingDegrees - getRadarHeading();
        turnRadarRight(Utils.normalRelativeAngleDegrees(radarTurnDegrees));
    }

    @Override
    public void onHitByBullet(HitByBulletEvent event) {
        //ahead(100);
        direction *= -1;
        ahead(100 * direction);
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        direction *= -1;
    }
}

