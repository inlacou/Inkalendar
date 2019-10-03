package com.inlacou.library.calendar.inkalendar.utils

import android.content.Context
import android.os.Build
import android.widget.ImageView

/**
 * This class is used to load event image in a day cell
 *
 *
 * Created by Mateusz Kornakiewicz on 23.05.2017.
 */

object ImageUtils {

	/**
	 * This method asynchronously loads image resource to ImageView object.
	 * If resource is 0 or null, or if imageView is null then method is finished before loading.
	 *
	 * @param imageView An ImageView that contains an image
	 * @param resource  An image resource loaded to ImageView
	 */
	fun loadResource(context: Context, imageView: ImageView?, resource: Int?) {
		if (resource==null || resource == 0 || imageView==null) {
			return
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			imageView.setImageDrawable(context.resources.getDrawable(resource, null))
		}else{
			imageView.setImageDrawable(context.resources.getDrawable(resource))
		}
	}
}