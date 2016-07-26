#Convert the Char Arr to String

    public String reverseString(String s) {

        if(s==null) return "";

        if(s.equals("")) return "";

        if(s.length()==1) return s;

        char[] sCharArr=s.toCharArray();
        char[] anCharArr=new char[sCharArr.length];
        for(int i=0;i<sCharArr.length;i++){
            anCharArr[i]=s.charAt(sCharArr.length-1-i);
        }

        return anCharArr.toString();


    }
    
    It is wrong:
    
            return anCharArr.toString();
            
	You should use this:
	
	String.valueOf(anCharArr);