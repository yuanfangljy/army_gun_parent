public class TraditionalThread {
	
	public static long factorial(int n){
		if(n == 1){//递归体
			return 1;
		}else{//递归头
			return n*factorial(n-1);
		}
	}
	
		public static void main(String[] args) {
			long num = factorial(10);
			System.out.println(num);
		}
}	
