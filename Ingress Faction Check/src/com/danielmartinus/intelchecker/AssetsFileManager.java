package com.danielmartinus.intelchecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

public class AssetsFileManager {
	
	static String getJsFromAsset(Context ctx, String filename) {
		InputStream is = null;
		try {
			is = ctx.getResources().getAssets().open(filename);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));

			StringBuffer sb = new StringBuffer();
			String line = reader.readLine();
			while (line != null) {
				sb.append(line).append("\n");
				line = reader.readLine();
			}
			return sb.toString();
		} catch (IOException e) {
			 Log.e("HUNTER", "dsf");
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return "";
	}
	
}
