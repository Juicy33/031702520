import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lost {
  static String province;
  static String city;
  static String county;
  static String nowAdd;
  static ArrayList<String> level_one = new ArrayList<>();
  static ArrayList<String> level_two = new ArrayList<>();
  static ArrayList<String> level_three = new ArrayList<>();

  public static JSONObject addressResolution(String address) throws IOException {

    if (level_one.size() == 0) {
      InputStream stream = Lost.class.getClassLoader().getResourceAsStream("lostAdress");
      BufferedReader br = new BufferedReader(new InputStreamReader(stream, "utf-8"));
      String line = null;
      int position = 0;
      String[] bufstring = new String[20480];
      while ((line = br.readLine()) != null) {
        bufstring[position] = line;
        String[] a = bufstring[position].split("\\s+");
        if (a[0].substring(2).equals("0000")) {
          level_one.add(a[1]);
        } else if (a[0].substring(4).equals("00")) {
          level_two.add(a[1]);
        } else {
          level_three.add(a[1]);
        }
        position++;
      }
    }

    String split[] = address.split("!");
    JSONObject jsonObject = new JSONObject();
    String str = address.substring(0, 2);
    address = address.replace(str, "");
    address = address.replace("!", "");
    String aString = address;
    String splits[] = aString.split(",");
    String name = splits[0];
    jsonObject.put("姓名", name);
    address = address.replace(name, "");
    address = address.replace(",", "");
    address = address.replace(".", "");
    String phoneRegex = "\\d{11}";
    Matcher a = Pattern.compile(phoneRegex).matcher(address);
    String phoneNumber = null;

    province = "";
    city = "";
    county = "";
    nowAdd = "";
    if (a.find()) {
      phoneNumber = a.group();
      address = address.replace(phoneNumber, "");
    }
    jsonObject.put("手机", phoneNumber);
    if (address.equals("")) {
      return jsonObject;
    }
    JSONArray jsonArray = new JSONArray();
    nowAdd = address;
    getProvince(nowAdd);
    getCity(nowAdd);
    getcounty(nowAdd);
    jsonArray.put(province);
    jsonArray.put(city);
    jsonArray.put(county);
    if (split[0].substring(split[0].length() - 1, split[0].length()).equals("1")) {
      String regex;
      if (!county.equals("") && county.substring(county.length() - 1).equals("区")) {
        regex = "(?<town>.+?镇|.+街道)?(?<village>.*)";
      } else {
        regex = "(?<town>[^区]+?区|.+?镇|.+街道)?(?<village>.*)";
      }

      String town = null, road = null, number = null, village = null;
      Matcher m = Pattern.compile(regex).matcher(nowAdd);
      if (m.find()) {
        town = m.group("town");
        jsonArray.put(town == null ? "" : town.trim());
        village = m.group("village");
        jsonArray.put(village == null ? "" : village.trim());
      }
      jsonObject.put("地址", jsonArray);
      return jsonObject;
    } else if (split[0].substring(split[0].length() - 1, split[0].length()).equals("2")) {
      String regex;
      if (!county.equals("") && county.substring(county.length() - 1).equals("区")) {
        regex = "(?<town>.+?镇|.+?街道|.+?乡)?(?<village1>.+?街|.+?路|.+?巷)?(?<village2>[\\d]+?号|[\\d]+.?道)?(?<village3>.*)";
      } else {
        regex = "(?<town>[^区]+?区|.+?镇|.+?街道|.+?乡)?(?<village1>.+?街|.+?路|.+?巷)?(?<village2>[\\d]+?号|[\\d]+.?道)?(?<village3>.*)";
      }
      String town = null, road = null, number = null, village = null;
      Matcher m = Pattern.compile(regex).matcher(nowAdd);

      if (m.find()) {
        town = m.group("town");
        jsonArray.put(town == null ? "" : town.trim());
        String village1 = m.group("village1");
        jsonArray.put(village1 == null ? "" : village1.trim());
        String village2 = m.group("village2");
        jsonArray.put(village2 == null ? "" : village2.trim());
        String village3 = m.group("village3");
        jsonArray.put(village3 == null ? "" : village3.trim());
      }
      jsonObject.put("地址", jsonArray);
      return jsonObject;
    } else {
      return new JSONObject();
    }

  }


  public static void main(String[] args) throws IOException {

    JSONArray jsonArray = new JSONArray();



    File fin = new File(args[0]);
    FileInputStream in = new FileInputStream(fin);
    BufferedReader br = new BufferedReader((new InputStreamReader(in, "UTF-8")));
    String line = null;
    int position = 0;
    String[] bufstring = new String[4096000];
    while ((line = br.readLine()) != null) {
      bufstring[position] = line;


      position++;
    }

    br.close();//关闭文件
//    for(int i=0;i<position;i++) {
//      System.out.println(bufstring[i]);}
    for (int i = 0; i < position; i++) {
      jsonArray.put(addressResolution(bufstring[i]));
    }
    File file = new File(args[1]);
    FileOutputStream out = new FileOutputStream(file, true);

    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));


    bw.write(jsonArray.toString());
    bw.flush();
  }

  private static void getProvince(String add) {
    int len = 0;
    for (String s : level_one) {
      String sub = s.substring(s.length() - 1);
      if (sub.equals("省")) {
        if (add.substring(0, s.length() - 1).equals(s.substring(0, s.length() - 1))) {
          province = s;
          len = s.length() - 1;
          if (add.charAt(s.length() - 1) == '省') {
            len = s.length();
          }
          break;
        }
      } else if (sub.equals("市")) {
        if (add.substring(0, s.length() - 1).equals(s.substring(0, s.length() - 1))) {
          province = s.substring(0, s.length() - 1);
          city = s;
          len = s.length() - 1;
          if (add.charAt(s.length() - 1) == '市') {
            len = s.length();
          }
          break;
        }

      } else if (s.substring(s.length() - 3).equals("自治区")) {
        if (add.substring(0, s.length() - 3).equals(s.substring(0, s.length() - 3))) {
          province = s;
          len = s.length() - 3;
          if (add.substring(s.length() - 3, s.length()).equals("自治区")) {
            len = s.length();
          }
          break;
        }
      }
    }

    nowAdd = add.substring(len);
    return;
  }

  private static void getCity(String add) {
    if (!city.equals("")) {
      return;
    }
    int len = 0;
    for (String s : level_two) {
      String sub = s.substring(s.length() - 1);
      if (sub.equals("市") || sub.equals("区")) {
        if (add.substring(0, s.length() - 1).equals(s.substring(0, s.length() - 1))) {
          city = s;
          len = s.length() - 1;
          if (add.charAt(s.length() - 1) == '区' || add.charAt(s.length() - 1) == '市') {
            len = s.length();
          }
        }
      } else if (s.substring(s.length() - 3).equals("自治州")) {
        if (add.substring(0, s.length() - 3).equals(s.substring(0, s.length() - 3))) {
          city = s;
          len = s.length() - 3;
          if (add.substring(s.length() - 3, s.length()).equals("自治州")) {
            len = s.length();
          }
        }
      }
    }
    nowAdd = add.substring(len);
    return;
  }


  private static void getcounty(String add) {
    int len = 0;
    for (String s : level_three) {
      if (add.length() > s.length() && (add.substring(0, s.length()).equals(s))) {
        county = s;
        len = s.length();
      }
    }
    nowAdd = add.substring(len);
    return;
  }
  private String responseFormat(String resString){

    StringBuffer jsonForMatStr = new StringBuffer();
    int level = 0;
    for(int index=0;index<resString.length();index++)//将字符串中的字符逐个按行输出
    {
      //获取s中的每个字符
      char c = resString.charAt(index);

      //level大于0并且jsonForMatStr中的最后一个字符为\n,jsonForMatStr加入\t
      if (level > 0  && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
        jsonForMatStr.append(getLevelStr(level));
      }
      //遇到"{"和"["要增加空格和换行，遇到"}"和"]"要减少空格，以对应，遇到","要换行
      switch (c) {
        case '{':
        case '[':
          jsonForMatStr.append(c + "\n");
          level++;
          break;
        case ',':
          jsonForMatStr.append(c + "\n");
          break;
        case '}':
        case ']':
          jsonForMatStr.append("\n");
          level--;
          jsonForMatStr.append(getLevelStr(level));
          jsonForMatStr.append(c);
          break;
        default:
          jsonForMatStr.append(c);
          break;
      }
    }
    return jsonForMatStr.toString();
  }

  private String getLevelStr(int level) {
    StringBuffer levelStr = new StringBuffer();
    for (int levelI = 0; levelI < level; levelI++) {
      levelStr.append("\t");
    }
    return levelStr.toString();
  }
}

