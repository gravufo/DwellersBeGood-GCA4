package com.dwellersbegood;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class IntroView extends SurfaceView implements SurfaceHolder.Callback
{
	private Bitmap m_IntroBitmap;
	private final Resources m_Res;
	private IntroThread m_Thread;
	private final Activity m_Activity;
	private int m_ScreenWidth;
	private int m_ScreenHeight;
	private final Paint m_Paint;
	
	public IntroView(Context context, Activity _Activity, Resources _Res)
	{
		super(context);
		getHolder().addCallback(this);
		this.m_Activity = _Activity;
		_Activity.setContentView(this);
		this.m_Res = _Res;
		this.m_Paint = new Paint();
		this.m_Paint.setAlpha(0);
		this.m_IntroBitmap = BitmapFactory.decodeResource(this.m_Res, R.drawable.intro);
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		if (canvas != null)
		{
			canvas.drawBitmap(this.m_IntroBitmap, 0, 0, this.m_Paint);
		}
	}
	
	public void Update()
	{
		this.m_Paint.setAlpha(this.m_Paint.getAlpha() + 1);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		// TODO Auto-generated method stub
		
		this.m_ScreenWidth = this.getWidth();
		this.m_ScreenHeight = this.getHeight();
		
		float scaleWidth = ((float) this.m_ScreenWidth) / (float) this.m_IntroBitmap.getWidth();
		float scaleHeight = ((float) this.m_ScreenHeight) / (float) this.m_IntroBitmap.getHeight();
		
		Matrix scaleMatrix = new Matrix();
		scaleMatrix.postScale(scaleWidth, scaleHeight);
		
		this.m_IntroBitmap = Bitmap.createBitmap(this.m_IntroBitmap, 0, 0, this.m_IntroBitmap.getWidth(), this.m_IntroBitmap.getHeight(), scaleMatrix, true);
		
		this.m_Thread = new IntroThread(getHolder(), this);
		this.m_Thread.setRunning(true);
		this.m_Thread.start();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		// TODO Auto-generated method stub
		
	}
	
	public void FinishIntro()
	{
		this.m_Activity.finish();
	}
	
}
