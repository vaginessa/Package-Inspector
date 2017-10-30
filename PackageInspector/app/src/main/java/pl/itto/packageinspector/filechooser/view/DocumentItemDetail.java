package pl.itto.packageinspector.filechooser.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import pl.itto.packageinspector.R;
import pl.itto.packageinspector.utils.DisplayUtils;

/**
 * Created by PL_itto on 10/26/2017.
 */

public class DocumentItemDetail extends FrameLayout {
    ImageView mTypeIcon;
    TextView mFileName;

    public DocumentItemDetail(@NonNull Context context) {
        super(context);
        mFileName = new TextView(context);
        mFileName.setTextColor(0xff212121);
        mFileName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        mFileName.setMaxLines(2);
        mFileName.setEllipsize(TextUtils.TruncateAt.END);
        mFileName.setGravity(Gravity.START);
        addView(mFileName);
        LayoutParams layoutParams = (LayoutParams) mFileName.getLayoutParams();
        layoutParams.width = LayoutParams.WRAP_CONTENT;
        layoutParams.height = LayoutParams.WRAP_CONTENT;
        layoutParams.setMarginStart(DisplayUtils.dp(60));
        layoutParams.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
        mFileName.setLayoutParams(layoutParams);

        mTypeIcon = new ImageView(context);
        mTypeIcon.setImageResource(R.drawable.ic_folder);
        addView(mTypeIcon);
        layoutParams = (LayoutParams) mTypeIcon.getLayoutParams();
        if (layoutParams == null)
            layoutParams = new LayoutParams(DisplayUtils.dp(50), DisplayUtils.dp(50));
        layoutParams.width = DisplayUtils.dp(50);
        layoutParams.height = DisplayUtils.dp(50);
        layoutParams.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
    }

    public DocumentItemDetail(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DocumentItemDetail(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void updateFileName(@NonNull String name) {
        mFileName.setText(name);
    }

}
