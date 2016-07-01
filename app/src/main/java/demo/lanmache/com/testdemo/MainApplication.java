/*
 * 
 * Copyright (c) 2015, alipay.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package demo.lanmache.com.testdemo;

import android.app.Application;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;

/**
 * sample application
 * 
 * @author sanping.li@alipay.com
 * 
 */
public class MainApplication extends Application {
	private static final String TAG = "euler";

	private static MainApplication mInstance = null;
	/**
	 * patch manager
	 */
	private PatchManager mPatchManager;

	public PatchManager getmPatchManager() {
		return mPatchManager;
	}

	public static MainApplication getInstance() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// initialize
		mPatchManager = new PatchManager(this);
		mPatchManager.init("1.0");
		Log.d(TAG, "inited.");
		// load patch
		mPatchManager.loadPatch();
		Log.d(TAG, "apatch loaded.");
		mInstance = this;

	}
}
