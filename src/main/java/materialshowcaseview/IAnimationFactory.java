package materialshowcaseview;

import android.graphics.Point;
import android.view.View;


/**
 * Author:  梁铖城
 * Email:   1038127753@qq.com
 * Date:
 * Description:
 */
public interface IAnimationFactory {

    void fadeInView(View target, long duration, AnimationStartListener listener);

    void fadeOutView(View target, long duration, AnimationEndListener listener);

    void animateTargetToPoint(MaterialShowcaseView showcaseView, Point point);

    public interface AnimationStartListener {
        void onAnimationStart();
    }

    public interface AnimationEndListener {
        void onAnimationEnd();
    }
}

