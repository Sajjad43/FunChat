package com.example.text;

import java.io.ByteArrayOutputStream;

import java.io.IOException;



import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ImageFragment extends Fragment implements OnClickListener{

	String uri="";
	byte [] data;
	EditText editext;
	Bitmap bitmap;
	String[] namePhone;

	public ImageFragment(String uri) {

		this.uri = uri;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		Log.d("Sajjad","ImageFragment");
		FileStatus.writelog("ImageFragment");

		//extract the View
		View view=inflater.inflate(R.layout.image_frag,null);
		ImageView image=(ImageView)view.findViewById(R.id.fullimageView2);
		editext=(EditText)view.findViewById(R.id.fulleditText2);
		Button button=(Button)view.findViewById(R.id.fullbutton2);
		button.setOnClickListener(this);

		try{

			//load the image
			LoadImage_SDcard imageloading=new LoadImage_SDcard(image,Uri.parse(uri),this.getActivity(),1);
			imageloading.start();

			//get the mobile user name and the number
			String userNameAndphone=readfile();
			namePhone=new String[2];
			namePhone=userNameAndphone.split(" ");	

			Log.d("Sajjad", "In ImageFragment,The username and phone are "+userNameAndphone);
		}catch(Exception e){

			FileStatus.writelog("ImageFragment,Exception-"+e.getLocalizedMessage());
		}

		return  view;
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		System.out.println(GroupImageActivity.selectedname);

		if(bitmap!=null)
		{
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {

						//convert the bitmap to byte array
						ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
						if(bitmap.compress(CompressFormat.JPEG, 50,outputStream)){

							data=new byte[outputStream.toByteArray().length];
							data=outputStream.toByteArray();

						}
						
						outputStream.close();
						
						//sent the friend list,friend list size,byte array, byte size,filename,mobile user number
						Service_Socket.service.imageUpload. outputStream.writeUTF(namePhone[1]);
						Service_Socket.service.imageUpload. outputStream.writeUTF(editext.getText().toString());
						Service_Socket.service.imageUpload. outputStream.writeInt(GroupImageActivity.selectedname.size());

						for(int i=0;i<GroupImageActivity.selectedname.size();i++){

							Service_Socket.service.imageUpload.outputStream.writeUTF(GroupImageActivity.selectedname.get(i));

						}



						Service_Socket.service.imageUpload.outputStream.writeInt(data.length);
						Service_Socket.service.imageUpload.outputStream.write(data,0,data.length);

						Log.d("Sajjad","Bitmap sent from ImageFragment");


					} catch (Exception e){
						// TODO Auto-generated catch block
						Log.d("Sajjad","In Imagefrag onclick(),Exception- "+e.getLocalizedMessage());
						FileStatus.writelog("In Imagefrag onclick(),Exception- "+e.getLocalizedMessage());
					}

				}

			}).start();


			//close the GroupImage activity
			this.getActivity().finish();

		}


	}


	public String readfile(){
		//read the mobile user name and number from the File 
		FileStatus file=new FileStatus(Service_Socket.service.getApplicationContext());
		return file.readfromfile("profile");


	}




}
