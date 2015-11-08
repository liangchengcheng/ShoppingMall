package materialshowcaseview;

import android.graphics.Point;

/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:
 * Description:
 */
public interface Target {
    Target NONE = new Target() {
        @Override
        public Point getPoint() {
            return new Point(1000000, 1000000);
        }

        @Override
        public int getRadius() {
            return 200;
        }
    };

    Point getPoint();

    int getRadius();
}
