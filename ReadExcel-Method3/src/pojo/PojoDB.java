package pojo;

public class PojoDB {

public String getTopic_id() {
	return topic_id;
}
public void setTopic_id(String topic_id) {
	this.topic_id = topic_id;
}
public String getQcontent() {
	return qcontent;
}
public void setQcontent(String qcontent) {
	this.qcontent = qcontent;
}
public String getInput() {
	return input;
}
public void setInput(String input) {
	this.input = input;
}
public String getOutput() {
	return output;
}
public void setOutput(String output) {
	this.output = output;
}
public String getNoattempted() {
	return noattempted;
}
public void setNoattempted(String noattempted) {
	this.noattempted = noattempted;
}
public String getNoaccepted() {
	return noaccepted;
}
public void setNoaccepted(String noaccepted) {
	this.noaccepted = noaccepted;
}
public String getDifficulity() {
	return difficulity;
}
public void setDifficulity(String difficulity) {
	this.difficulity = difficulity;
}
 
private String topic_id;
private String qcontent;
  private String input;
  private String output;
  private String noattempted;
  private String noaccepted;
  private String difficulity;
  private String half_finished_sourcecode;


private String sampleinput;
  private String sampleoutput;
  private String title;
  private String hint; //暗示
  private String timelimite;
  private String memorylimit;
  private String frompaper_source;
//  private String spj;  // 是否是特殊题目
//  private String data; //没有用，两个数据库均不包含该项
//  private String defunt; //是否屏蔽


private String answercode;
	// 第一行 第0列试题名称
	// 第一行 第1列试题所属类别号
	// 第一行 第2列试题所属类
	// 第一行 第3列试题主体
	// 第一行 第4列试题输入描述
	// 第一行 第5列试题输出描述
	// 第一行 第6列试题样例输入
	// 第一行 第7列试题样例输出
	// 第一行 第8列小提示
	// 第一行 第9列试题运行时间限制
	// 第一行 第10列试题运行内存限制
	// 第一行 第11列试题（PDF名称）类型
	// 第一行 第12列填空题目的格式(原型，模板)
	// 第一行 第13列样例答案
	// 第一行 第14列难度
public String getFrompaper_source() {
	return frompaper_source;
}
public void setFrompaper_source(
		String frompaper_source) {
	this.frompaper_source = frompaper_source;
}
public String getHalf_finished_sourcecode() {
	return half_finished_sourcecode;
}
public void setHalf_finished_sourcecode(
		String half_finished_sourcecode) {
	this.half_finished_sourcecode = half_finished_sourcecode;
}

public String getSampleinput() {
	return sampleinput;
}
public void setSampleinput(
		String sampleinput) {
	this.sampleinput = sampleinput;
}
public String getSampleoutput() {
	return sampleoutput;
}
public void setSampleoutput(
		String sampleoutput) {
	this.sampleoutput = sampleoutput;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getHint() {
	return hint;
}
public void setHint(String hint) {
	this.hint = hint;
}
public String getTimelimite() {
	return timelimite;
}
public void setTimelimite(
		String timelimite) {
	this.timelimite = timelimite;
}
public String getMemorylimit() {
	return memorylimit;
}
public void setMemorylimit(
		String memorylimit) {
	this.memorylimit = memorylimit;
}
 
public String getAnswercode() {
	return answercode;
}
public void setAnswercode(
		String answercode) {
	this.answercode = answercode;
}

  
  
  
}
