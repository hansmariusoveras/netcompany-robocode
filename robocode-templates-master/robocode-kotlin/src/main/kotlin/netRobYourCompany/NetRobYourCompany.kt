package netRobYourCompany

import robocode.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.atan2
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import java.awt.Color;
import java.util.*

// TODO:
// Don't hit walls or other robots
// Always move
// Always scan
// Shoot on scan

class NetRobYourCompany : AdvancedRobot() {
    override fun run() {
        this.isAdjustGunForRobotTurn = true;
        this.setAllColors(Color.PINK);
        this.setBodyColor(Color.MAGENTA)
        this.setGunColor(Color.PINK)
        this.setRadarColor(Color.ORANGE)
        setScanColor(Color.white);
        setBulletColor(Color.pink);
        var mode = 0;
        while (true) {
            if (this.x > this.battleFieldWidth - 75) {
                mode = 0
                if (!(259 < this.heading && this.heading < 271)) {
                    this.setTurnRight(8.0);
                } else {
                    this.setAhead(8.0);
                    this.setAhead(8.0);
                }
            }; else if (this.x < 75) {
                mode = 0
                if (!(79 < this.heading && this.heading < 91)) {
                    this.setTurnRight(8.0);
                } else {
                    this.setAhead(8.0);
                    this.setAhead(8.0);
                }
            }; else if (this.y > this.battleFieldHeight - 75) {
                mode = 0
                if (!(191 > this.heading && this.heading > 170)) {
                    this.setTurnRight(8.0);
                } else {
                    this.setAhead(8.0);
                    this.setAhead(8.0);
                }
            }; else if (this.y < 75) {
                mode = 0
                if (!(11 > this.heading || this.heading > 349)) {
                    this.setTurnRight(8.0)
                } else {
                    this.setAhead(8.0);
                    this.setAhead(8.0);
                }
            } else {
                if (Random().nextInt(180) < 10) {
                    mode = (mode + 1) % 3
                }
                if (mode == 0) {
                    setAhead(8.0)
                } else if (mode == 1) {
                    setTurnLeft(3.0);
                    setAhead(8.0);
                } else if (mode == 2) {
                    setTurnRight(3.0);
                    setAhead(8.0);
                }
            }
            setTurnGunLeft(15.0);
            execute();
        }
    }

      override fun onScannedRobot(event: ScannedRobotEvent?) {
        if (event == null) {
            return;
        }
          var distance = event.distance;
          var maxDistance = min(500.0, max(battleFieldWidth, battleFieldHeight));
          if (distance > maxDistance) {
              return;
          }
        var power = (1-distance/maxDistance)*3.0;
          power = min(power, event.energy)

          if (energy < 5.0) {power = 0.1}
          else if (energy < 10.0) { power = 0.5}
        setFire(power)
    }

    // deprecated
    fun getDeltaAngle(event: ScannedRobotEvent): Double {
        var radarHeading = radarHeadingRadians
        var eventHeading = event.headingRadians
        var curPosX = x + event.distance * cos(radarHeading);
        var curPosY = y - event.distance * sin(radarHeading);
        var nextPosX = curPosX - event.velocity * cos(eventHeading);
        var nextPosY = curPosY + event.velocity * sin(eventHeading);
        var targetAngle = (- atan2(nextPosY - y, nextPosX - x)) % (2.0 * PI)

        var deltaAngle = targetAngle - gunHeadingRadians
        if (abs(deltaAngle) > PI) {
            deltaAngle = gunHeadingRadians - targetAngle
        }
        this.out.println(deltaAngle)

        return deltaAngle
    }

    override fun onWin(e: WinEvent) {
        for (i in 0..10) {
            this.turnRight(30.0);
            this.turnLeft(30.0);
        }
        this.fire(0.1);
        this.fire(0.1);
    }
}
