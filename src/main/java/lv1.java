
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class lv1 {
    JSONObject jsonObject = new JSONObject();
    public String address,phone,name,rank;
    String address1,last;
    String province = "",
            city = "",
            county = "",
            town = "",
            village = "",
            village1 = "" ,
            village2 = "";
    String[] Ccity={"北京","天津","上海","重庆"};
    String[] Cprovince={"河北","山西","辽宁","吉林","黑龙江","江苏","浙江","安徽","福建","江西","山东","河南","湖北","湖南","广东","海南","四川","贵州","云南","陕西","甘肃","青海","台湾"};
    String[] Pcity={"石家庄","唐山","秦皇岛","邯郸","邢台","保定","张家口","承德","沧州","廊坊","衡水",
            "太原","大同","阳泉","长治","晋城","朔州","晋中","运城","忻州","临汾","吕梁",
            "呼和浩特","包头","乌海","赤峰","通辽","鄂尔多斯","呼伦贝尔","巴彦淖尔","乌兰察布","兴安","锡林郭勒","阿拉善",
            "沈阳","大连","鞍山","抚顺","本溪","丹东","锦州","营口","阜新","辽阳","盘锦","铁岭","朝阳","葫芦岛",
            "长春","吉林","四平","辽源","通化","白山","松原","白城","延边",
            "哈尔滨","齐齐哈尔","鸡西","鹤岗","双鸭山","大庆","伊春","佳木斯","七台河","牡丹江","黑河","绥化","大兴安岭",
            "南京","无锡","徐州","常州","苏州","南通","连云港","淮安","盐城","扬州","镇江","泰州","宿迁",
            "杭州","宁波","温州","嘉兴","湖州","绍兴","金华","衢州","舟山","台州","丽水",
            "合肥","芜湖","蚌埠","淮南","马鞍山","淮北","铜陵","安庆","黄山","滁州","阜阳","宿州","巢湖","六安","亳州","池州","宣城",
            "福州","厦门","莆田","三明","泉州","漳州","南平","龙岩","宁德",
            "南昌","景德镇","萍乡","九江","新余","鹰潭","赣州","吉安","宜春","抚州","上饶",
            "济南","青岛","淄博","枣庄","东营","烟台","潍坊","威海","济宁","泰安","日照","莱芜","临沂","德州","聊城","滨州","菏泽",
            "郑州","开封","洛阳","平顶山","焦作","鹤壁","新乡","安阳","濮阳","许昌","漯河","三门峡","南阳","商丘","信阳","周口","驻马店",
            "武汉","黄石","襄樊","十堰","荆州","宜昌","荆门","鄂州","孝感","黄冈","咸宁","随州","恩施",
            "长沙","株洲","湘潭","衡阳","邵阳","岳阳","常德","张家界","益阳","郴州","永州","怀化","娄底","湘西",
            "广州","深圳","珠海","汕头","韶关","佛山","江门","湛江","茂名","肇庆","惠州","梅州","汕尾","河源","阳江","清远","东莞","中山","潮州","揭阳","云浮",
            "南宁","柳州","桂林","梧州","北海","防城港","钦州","贵港","玉林","百色","贺州","河池","来宾","崇左",
            "海口","三亚",
            "成都","自贡","攀枝花","泸州","德阳","绵阳","广元","遂宁","内江","乐山","南充","宜宾","广安","达州","眉山","雅安","巴中","资阳","阿坝","甘孜","凉山",
            "贵阳","六盘水","遵义","安顺","铜仁","毕节","黔西南","黔东南","黔南",
            "昆明","曲靖","玉溪","保山","昭通","丽江","普洱","临沧","文山","红河","西双版纳","楚雄","大理","德宏","怒江","迪庆",
            "拉萨","昌都","山南","日喀则","那曲","阿里","林芝",
            "西安","铜川","宝鸡","咸阳","渭南","延安","汉中","榆林","安康","商洛",
            "兰州","嘉峪关","金昌","白银","天水","武威","张掖","平凉","酒泉","庆阳","定西","陇南","临夏","甘南",
            "西宁","海东","海北","黄南","海南","果洛","玉树","海西",
            "银川","石嘴山","吴忠","固原","中卫",
            "乌鲁木齐","克拉玛依","吐鲁番","哈密","和田","阿克苏","喀什","克孜勒苏","克孜勒苏柯尔克孜","巴音郭楞蒙古","昌吉","博尔塔","博尔塔拉蒙古","伊犁","伊犁哈萨克","塔城","阿勒泰",
            "香港","澳门","台湾台北","台北","高雄","基隆","台中","台南","新竹","嘉义"};


    private void addressdivider(){
                                                                           //正则
        String[] add1 = address1.split(",");                       //匹配“，”
        name = add1[0];
        if (add1.length == 1)
            address1 = "";
        else
        address1 = add1[1];
        address1 = address1.replace(".","");           //name



        String phoneRegex = "\\d{11}";                                     //匹配11位电话号码
        Matcher matcher = Pattern.compile(phoneRegex).matcher(address1);   //phone
        while (matcher.find()){
        phone= matcher.group(0);
            matcher.find();
        }
        address = address1.replace(phone,"");                //获得地址
        address1 = address;
    }

    void WithPC(){
        AddzC();
        AddP();
        AddC();
    }              /*补一二级后缀*/

    void WithP(){
        AddP();
    }       /*补第一级后缀*/

    void WithC(){
        AddC();
    }       /*补第二级后缀*/

    void AddP(){
        int flag1 = -1;
        for (String x:Cprovince){
            flag1 = address.indexOf(x);
            if (flag1 >=0 ) {
                StringBuilder padder = new StringBuilder(address);
                if (x.equals("黑龙江")){
                    padder.insert(3,"省");
                }
                else{
                    padder.insert(2,"省");
                }
                address = padder.toString();
                break;
            }
        }
    }                                                                       //加入省




    void AddC(){
        int flag = -1;
        int len;
        for (String s : Pcity) {
            flag = address.indexOf(s);
            if (flag >= 0) {
                StringBuilder cadder = new StringBuilder(address);
                len = s.length();
                cadder.insert(flag+ len, "市");
                address = cadder.toString();
                break;
            }
        }
    }                                                                     //加入市

    void AddzC(){
        int flag = -1;
        for (String x:Ccity){
            flag = address.indexOf(x);
            if (flag >= 0){
                StringBuilder zcadder = new StringBuilder(address);
                zcadder.insert(2,"市");
                address = zcadder.toString();
                return;
            }
        }
    }                                                                    //加入直辖市

    void Scout(){   /*重置*/
        if (province == null){
            province = "";
        }
        if (city == null){
            city = "";
        }
        if (county == null){
            county = "";
        }if (town == null){
            town = "";
        }
        if (village == null){
            village = "";
        }
        if (village1 == null){
            village1 = "";
        }
        if (village2 == null){
            village2 = "";
        }
    }              /*把空指针重置*/

    void Solve(){
            last = ("{\"姓名\":\"" + name +"\",\"手机\":\""+phone+"\",\"地址\":[\""+province+"\",\""+city+"\",\""+county+"\",\""+town+"\",\""+village+"\"]}");

    }                                                         //输出

    void main(){
        addressdivider();
        if (province==null && city==null){
            WithPC();
            Rrank();
        }
        else if(province == null && city != null){
            WithP();
            Rrank();
        }
        else if(province != null && city == null){
            WithC();
            Rrank();
        }
        Scout();
        Solve();
    }
    void Strsolve() {
        String[] lev = address.split("!");    //以!分割 ->等级
        rank = lev[0];      //等级
        address1 = lev[1];
    }
    void Rank1(){
        String regexone="(?<province>[^省]+自治区|.*?省|.*?行政区)?(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市)?(?<county>[^县]+县|.+?区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇|.+街道)?(?<village>.*)";
        Matcher m = Pattern.compile(regexone).matcher(address);
        while (m.find()){
            province = m.group("province");
            city = m.group("city");
            county = m.group("county");
            town = m.group("town");
            village = m.group("village");
            m.find();
        }

        if (province == null && city != null){
            for (String x:Ccity){
                if (city.indexOf(x) >= 0){
                    String[] add= city.split("市");
                    province = add[0];
                    return;
                }
            }
        }
    }              /*等级1*/

    void Rank2(){
        String regex = "(?<village>.+?街|.+?路|.+?巷)?(?<village1>[\\d]+?号|[\\d]+.?道)?(?<village2>.*)";
        Matcher m = Pattern.compile(regex).matcher(village);
        while (m.find()){
            village = m.group("village");
            village1 = m.group("village1");
            village2 = m.group("village2");
            m.find();
        }
    }              /*等级2*/

    void Rank3(){
        Rank1();
        Rank2();
    }              /*等级3*/

    void Rrank(){   /*按等级执行*/
        if (rank.equals("1")){
            Rank1();
        }
        if (rank.equals("2")){
            Rank1();
            Rank2();
        }
        if (rank.equals("3")){
            Rank3();
        }
    }              /*根据难度选择*/
}




