public String minWindow(String S, String T) {
    int[] hasFound   = new int[256];
	int[] needToFind = new int[256];
	for(int i=0; i < T.length(); i++)
		++needToFind[T.charAt(i)];
	
	int minBegin       = 0;
	int minEnd         = 0;
	int minWindowLen   = Integer.MAX_VALUE;
	int foundCharCount = 0;
	
	for(int begin=0,end=0; end < S.length(); end++) {
		int ch = S.charAt(end);
		if(needToFind[ch] == 0)
			continue;
		++hasFound[ch];
		if(hasFound[ch] <= needToFind[ch])
			++foundCharCount;
		
		if(foundCharCount == T.length()) {
            while (needToFind[S.charAt(begin)] == 0 ||
                 hasFound[S.charAt(begin)] > needToFind[S.charAt(begin)]) {
    					if(hasFound[S.charAt(begin)] > needToFind[S.charAt(begin)])
    					--hasFound[S.charAt(begin)];
    					++begin;
    		}
			int len = end - begin +1;
			if(len < minWindowLen) {
				minBegin 	 = begin;
				minEnd   	 = end;
				minWindowLen = len;
		 }
    	//置为0进行下一次搜索??错误，不能添加这句话，为什么？？
        //foundCharCount = 0;
	}
	return minWindowLen <= S.length()?S.substring(minBegin, minEnd+1):"";
}


