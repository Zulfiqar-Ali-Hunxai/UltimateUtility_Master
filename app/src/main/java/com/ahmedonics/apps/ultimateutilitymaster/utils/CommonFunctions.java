package com.ahmedonics.apps.ultimateutilitymaster.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;

import com.ahmedonics.apps.ultimateutilitymaster.R;
import com.ahmedonics.apps.ultimateutilitymaster.activities.Others.CalendarActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonFunctions {

    public static String getMemorySizeHumanized() {
        RandomAccessFile reader;
        String load;
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        double totRam;
        String lastValue = "";

        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            load = reader.readLine();
            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(load);
            String value = "";
            while (m.find()) {
                value = m.group(1);
            }

            reader.close();

            totRam = Double.parseDouble(value);

            double mb = totRam / 1024.0;
            double gb = totRam / 1048576.0;
            double tb = totRam / 1073741824.0;

            if (tb > 1) {
                lastValue = twoDecimalForm.format(tb).concat(" TB");
            } else if (gb > 1) {
                lastValue = twoDecimalForm.format(gb).concat(" GB");
            } else if (mb > 1) {
                lastValue = twoDecimalForm.format(mb).concat(" MB");
            } else {
                lastValue = twoDecimalForm.format(totRam).concat(" KB");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return lastValue;
    }

    public static void setAddToHomeShortcut(final Context ctx, Button btn, final String iconLabel, final int iconRes) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShortcutManagerCompat.isRequestPinShortcutSupported(ctx)) {
                    ShortcutInfoCompat shortcutInfo = new ShortcutInfoCompat.Builder(ctx, "#1")
                            .setIntent(new Intent(ctx, CalendarActivity.class).setAction(Intent.ACTION_MAIN))
                            .setShortLabel(iconLabel)
                            .setIcon(IconCompat.createWithResource(ctx, iconRes))
                            .build();
                    ShortcutManagerCompat.requestPinShortcut(ctx, shortcutInfo, null);
                }
            }
        });
    }

    public static void resultSharingDialog(final Activity AV, String text) {
        final Dialog dialog;
        final String result = text;

        dialog = new Dialog(AV);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_custom_dialog);

        TextView tv_message = (TextView) dialog.findViewById(R.id.txt_dia);
        tv_message.setText(result);
        tv_message.setMovementMethod(new ScrollingMovementMethod());
        Button bt_yes = (Button )dialog.findViewById(R.id.btn_yes);
        Button bt_no = (Button) dialog.findViewById(R.id.btn_no);

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        bt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CommonFunctions.getSharingIntent(result, "plain/text");
                AV.startActivity(Intent.createChooser(intent, "Share using"));
            }
        });
        bt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public static void setupSettingsScreenAlwaysOn(Context ctx, Activity activity) {
        SharedPreferences pref = ctx.getSharedPreferences("appSettings", 0);
        boolean check = pref.getBoolean("keep_screen_on_", false);
        // String userName = pref.getString("user_name_", "");
        if (check) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    public static int generateRandomNumber(int startValue, int endValue) {
        Random r = new Random();
        int random = r.nextInt(endValue) + startValue;
        return random;
    }

    public static boolean isEmptyEditText(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public static int getEditTextAsInt(EditText et) {
        String sTextFromET = et.getText().toString();
        int value = Integer.valueOf(sTextFromET);
        return value;
    }

    public static int getTotalCharsInText(String text) {
        int totalChars = 0;
        for (int i = 0; i < text.length(); i++) {
            totalChars++;
        }
        return totalChars;
    }

    public static int getTotalWordsInText(String text) {
        String[] words = text.split("\\s+");
        int totalWords = words.length;
        return totalWords;
    }

    public static int getTotalLinesInText(String text) {
        String[] lines = text.split("\r\n|\r|\n");
        int totalLines = lines.length;
        return totalLines;
    }

    public static Intent getSharingIntent(String shareText, String sharingIntentType) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType(sharingIntentType);
        //String shareSub = "Your subject here";
        //sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
        return sharingIntent;
    }

    public static String getPublicIPAddress() {
        String value = null;
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<String> result = es.submit(new Callable<String>() {
            public String call() throws Exception {
                try {
                    URL url = new URL("http://ip.ip-check.net");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader r = new BufferedReader(new InputStreamReader(in));
                        StringBuilder total = new StringBuilder();
                        String line;
                        while ((line = r.readLine()) != null) {
                            total.append(line).append('\n');
                        }
                        urlConnection.disconnect();
                        return total.toString();
                    }finally {
                        urlConnection.disconnect();
                    }
                }catch (IOException e){
                    Log.e("Public IP: ",e.getMessage());
                }
                return null;
            }
        });
        try {
            value = result.get();
        } catch (Exception e) {
            // failed
        }
        es.shutdown();
        return value;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sbuf = new StringBuilder();
        for(int idx=0; idx < bytes.length; idx++) {
            int intVal = bytes[idx] & 0xff;
            if (intVal < 0x10) sbuf.append("0");
            sbuf.append(Integer.toHexString(intVal).toUpperCase());
        }
        return sbuf.toString();
    }

    public static byte[] getUTF8Bytes(String str) {
        try { return str.getBytes("UTF-8"); } catch (Exception ex) { return null; }
    }

    public static String loadFileAsString(String filename) throws java.io.IOException {
        final int BUFLEN=1024;
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(filename), BUFLEN);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFLEN);
            byte[] bytes = new byte[BUFLEN];
            boolean isUTF8=false;
            int read,count=0;
            while((read=is.read(bytes)) != -1) {
                if (count==0 && bytes[0]==(byte)0xEF && bytes[1]==(byte)0xBB && bytes[2]==(byte)0xBF ) {
                    isUTF8=true;
                    baos.write(bytes, 3, read-3); // drop UTF8 bom marker
                } else {
                    baos.write(bytes, 0, read);
                }
                count+=read;
            }
            return isUTF8 ? new String(baos.toByteArray(), "UTF-8") : new String(baos.toByteArray());
        } finally {
            try{ is.close(); } catch(Exception ignored){}
        }
    }

}
