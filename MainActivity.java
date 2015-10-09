package com.example.sikhnetradio;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private HashMap<String, String> urlMap; 
	private MediaPlayer mediaPlayer;
	private String currentURL = "";
	private String previousURL = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		urlMap = new HashMap<String, String>();
		
		populateURLMap();
		populateListView();
		registerClickCallback();
		
		Button playButton = (Button) findViewById(R.id.play_btn);
		Button pauseButton = (Button) findViewById(R.id.pause_btn);
		
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		
		playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!currentURL.equals(previousURL)) {
					mediaPlayer.reset();
					try {
						mediaPlayer.setDataSource(currentURL);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						mediaPlayer.prepare();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} // might take long! (for buffering, etc)
				}
				mediaPlayer.start();
			}
		});
		
		pauseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mediaPlayer.pause();
				previousURL = currentURL;
			}
		});
	}

	private void populateURLMap() {
		urlMap.put("Harimandir Sahib (Amritsar, Punjab, India)", "http://radio2.sikhnet.com:8020/live");
		urlMap.put("Dukh Niwaran Sahib (Ludhiana, Punjab, India)", "http://radio2.sikhnet.com:8022/live");
		urlMap.put("Takhat Hazur Sahib (Nanded, Maharashtra, India)", "http://radio2.sikhnet.com:8038/live");
		urlMap.put("Dasmesh Darbar (Surrey, BC. Canada)", "http://radio2.sikhnet.com:8036/live");
		urlMap.put("Dukh Nivaran Sahib (Surrey, BC. Canada)", "http://radio2.sikhnet.com:8037/live");
		urlMap.put("Fremont (Fremont, California, USA)", "http://radio2.sikhnet.com:8026/live");
		urlMap.put("Dashmesh Culture Center (Calgary, Alberta, Canada)", "http://radio2.sikhnet.com:8030/live");
		urlMap.put("Guru Nanak Sikh Gurdwara (Surrey, BC. Canada)", "http://radio2.sikhnet.com:8034/live");
		urlMap.put("Gurdwara San Jose (San Jose, California, USA)", "http://radio2.sikhnet.com:8031/live");
		urlMap.put("Western Singh Sabha (Williams Lake, BC. Canada)", "http://radio2.sikhnet.com:8029/live");
		urlMap.put("Sukh Sagar (New Westminster, BC. Canada)", "http://radio2.sikhnet.com:8023/live");
		urlMap.put("Hacienda De Guru Ram Das (Espanola, New Mexico, USA)", "http://radio2.sikhnet.com:8024/live");
		urlMap.put("Singh Sabha Washington (Renton, Washington, USA)", "http://radio2.sikhnet.com:8032/live");
		urlMap.put("Singh Sabha Surrey (Surrey, BC. Canada)", "http://radio2.sikhnet.com:8033/live");
		urlMap.put("Gurdwara SW Houston (Southwest Houston, Texas, USA)", "http://radio2.sikhnet.com:8035/live");
		urlMap.put("Siri Guru Singh Sabha (Derby, UK)", "http://radio2.sikhnet.com:8040/live");
		urlMap.put("Sacramento Sikh Society (Sacramento, California, USA)", "http://radio2.sikhnet.com:8041/live");
		urlMap.put("Nanak Naam Jahaj (New Jersey, USA)", "http://radio2.sikhnet.com:8042/live");
		urlMap.put("El Sobrante (El Sobrante, California, USA)", "http://radio2.sikhnet.com:8043/live");
		urlMap.put("Stockton Sikh Temple (Stockton, California, USA)", "http://radio2.sikhnet.com:8045/live");
		urlMap.put("Sri Guru Singh Sabha Malton (Mississauga, ON. Canada)", "http://radio2.sikhnet.com:8039/live");
		urlMap.put("Gurdwara Sahib West Sacramento (Sacramento, CA, USA)", "http://radio2.sikhnet.com:8019/live");
		urlMap.put("Gurdwara Sahib Glenwood (Sydney, Australia)", "http://radio2.sikhnet.com:8047/live");
	}

	private void populateListView() {
		String[] gurdwaras = new String[urlMap.size()];
		int index = 0;
		for(String name: urlMap.keySet()) {
			gurdwaras[index] = name;
			index++;
		}
		
		Arrays.sort(gurdwaras);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this,			//context for the activity
				R.layout.item, 	//layout to use (create)
				gurdwaras);		//items to be displayed
		
		ListView list = (ListView) findViewById(R.id.listViewMain);
		list.setAdapter(adapter);
	}
	
	private void registerClickCallback() {
		ListView list = (ListView) findViewById(R.id.listViewMain);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
				TextView textView = (TextView) viewClicked;
				previousURL = currentURL;
				if(!urlMap.get(textView.getText().toString()).equals(previousURL)) {
					currentURL = urlMap.get(textView.getText().toString());
				}
			}
		});
	}
}
