import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Integer;

public class KernighanLin {
	
	 private int[][] graph ;
	 private int n;
	 public ArrayList<Integer> A =new ArrayList<Integer>();//partition A--array list of integers standing for vertices
	 public ArrayList<Integer> B =new ArrayList<Integer>();//partition B
	 HashMap<Integer, Integer> D = new HashMap<Integer,Integer>(); //key value pair of <vertex, D>
     private int[][] lockedPairs;//local variable for a pass
     int lockCounter;
     double sumGain;
	 int swapPosition; 
	 double maxSumGain; 
	 int cutCost;

	 
	 
	 //constructor 
	 
	 public KernighanLin(int[][] i){
		 
		 this.graph = i;
		 this.n = i[0].length;
		 this.lockedPairs = new int[3][n/2];
	 
	 }
	 
     public void process(){
    	 int f=1;
    	 initializePartition();
//    	 A.add(0);A.add(1); A.add(2);A.add(3);
//    	 B.add(4);B.add(5); B.add(6);B.add(7);
//    	 System.out.println("partition A is :" + A);
//    	 System.out.println("partition B is :" + B);
    	 do{
    		 lockCounter=0;//renew for each pass
        	 swapPosition=0; //renew for each pass
        	 sumGain=0;
        	 maxSumGain=Double.NEGATIVE_INFINITY; //renew for each pass
        	 lockedPairs =  new int[3][n/2];
        	 cutCost=0;

    	//	 System.out.println("MaxSumGain:"+maxSumGain);
			 calculateD();	
			 System.out.println("cutCost:" + cutCost);
    		 for(int i=0;i<n/2;i++){
    			 A = quicksort(A);
    			 B = quicksort(B);
    			 System.out.println("sorted partition A is :" + A);
    			 System.out.println("sorted partition B is :" + B);
    			 lockPairsWithMaxGain();   			
    			 
    			 // print out lockpairs
    				for (int z= 0; z < lockedPairs.length; z++) {
    				    for (int j = 0; j < lockedPairs[z].length; j++) {
    				        System.out.print(lockedPairs[z][j] + " ");
    				    }
    				    System.out.println("---------------------------");
    				}
    			 updateD();
    	     }
    		 System.out.println("MaxSumGain:"+maxSumGain);
			 System.out.println("swapPosition:"+swapPosition);
    		 swap();
			 if (maxSumGain < 0||maxSumGain==0) {return;}
    		  System.out.println("total pass"+ f);
    		  f++;
 			// if (f>1) {return;}

    	 }while(true);
    	
     }
	 public void calculateD () {
		
		for(int i=0;i<n/2;i++){
			int Ea = 0,Eb=0;//external cost
			int Ia = 0,Ib=0;//internal cost
			int Ai=Integer.valueOf(A.get(i));
			int Bi=Integer.valueOf(B.get(i));
			
			for(int j=0;j<n/2;j++){
				
				int Aj=Integer.valueOf(A.get(j));
				int Bj=Integer.valueOf(B.get(j));
				Ea+=graph[Ai][Bj];//add all external cost for each vertex in partition A
				Ia+=graph[Ai][Aj];//sub all internal cost for each vertex in partition A
				Eb+=graph[Bi][Aj];//add all external cost for each vertex in partition A
				Ib+=graph[Bi][Bj];//sub all internal cost for each vertex in partition A
			}
			 System.out.println("D"+Ai+"="+(Ea-Ia));
			 System.out.println("D"+Bi+"="+(Eb-Ib));
			D.put(Ai, Ea-Ia);
			D.put(Bi, Eb-Ib);
            cutCost+=Ea;
            
		}
	  
	  }
	public void updateD(){
		int size=A.size();

		for(int i=0;i<size;i++){
			int Ai=Integer.valueOf(A.get(i));
			int Bi=Integer.valueOf(B.get(i));
			//for x∈A Dx’=Dx + 2cxa- 2cxb      
			int newD = Integer.valueOf(D.get(Ai))+2*graph[Ai][lockedPairs[0][lockCounter-1]]-2*graph[Ai][lockedPairs[1][lockCounter-1]];
			D.put(Ai, newD);
			//System.out.println("cxb="+graph[Ai][lockedPairs[1][lockCounter]]);
			System.out.println("newD"+Ai+"="+newD);

			//for y∈B Dy’=Dy + 2cyb- 2cya
			newD = Integer.valueOf(D.get(Bi))+2*graph[Bi][lockedPairs[1][lockCounter-1]]-2*graph[Bi][lockedPairs[0][lockCounter-1]];
			D.put(Bi, newD);
			System.out.println("newD"+Bi+"="+newD);
		}
	  
	}
	
	public double calculateGain (int a, int b){         //g=Da+Db -2cab
		double gain=Integer.valueOf(D.get(a))+Integer.valueOf(D.get(b))-2*graph[a][b];
		return gain;
	}
	
	public void lockPairsWithMaxGain(){
		double maxGain=Double.NEGATIVE_INFINITY;
		int I=0, J=0;
		
		int size=A.size();

		for(int i=0;i<size;i++){
			
			int Ai=Integer.valueOf(A.get(i));
			System.out.println("A"+i+"="+Ai);
			for(int j=0;j<size;j++){
				
				int Bj=Integer.valueOf(B.get(j));
				System.out.println("B"+j+"="+Bj);

				int Dai=Integer.valueOf(D.get(Ai));
				int Dbj=Integer.valueOf(D.get(Bj));
				System.out.println("D"+Ai+"="+Dai+"D"+Bj+"="+Dbj);
				System.out.println("maxGain"+"="+maxGain);

				if((Dai+Dbj) > maxGain){ // go on to calculate gain if Dai+Dbj>maxGain
					double gain=calculateGain(Ai,Bj);
					System.out.println("Gain"+Ai+Bj+"="+gain);

					if(gain>maxGain){ //make current gain the maxGain and put the points name to locked pairs array
					 maxGain=gain;
					System.out.println("maxGain"+"="+maxGain);

				     lockedPairs[0][lockCounter]=Ai;
				     lockedPairs[1][lockCounter]=Bj;
				     I = i;
				     J = j;
			     } 
			   }
			  else {//finish calculating gain if Dai+Dbj<=maxGain, the pair with maxGain is locked 
				 A.remove(I);//remove the locked point from A
				 B.remove(J);//remove the locked point from B
       //          System.out.println("maxGain:"+maxGain);
				 sumGain+=maxGain;//add the maxGain the sum of maxGain
				 System.out.println("sumGain ="+sumGain);
		    	 if(sumGain>maxSumGain){maxSumGain=sumGain; swapPosition=lockCounter;    		 System.out.println("MaxSumGain= "+maxSumGain);
} // update the maxSumGain and swap position for the swap step
		    	 lockedPairs[2][lockCounter]=(int)sumGain; 
		    	 lockCounter++;
		    	 return;
		       }
		
		   }
		
	   } /*the following lines will only be used if the last pair has the largest gain*/
		//finish calculating gain if Dai+Dbj<=maxGain, the pair with maxGain is locked 
		 A.remove(I);//remove the locked point from A
		 B.remove(J);//remove the locked point from B
   	     sumGain+=maxGain;
    	 if(sumGain>maxSumGain){maxSumGain=sumGain; swapPosition=lockCounter;}
		 lockedPairs[2][lockCounter]=(int)sumGain;
		 lockCounter++;
		 return;
   }
	
   public void swap(){
	 if(maxSumGain>0){
	   for(int i=0;i<swapPosition+1;i++){
		   A.add( lockedPairs[1][i]);
		   B.add(lockedPairs[0][i]);   
	   }
	   for(int i=swapPosition+1;i<n/2;i++){
		   A.add( lockedPairs[0][i]);
		   B.add(lockedPairs[1][i]);   
	   }
	 } else
	 {
		 for(int i=0;i<n/2;i++){
			   A.add( lockedPairs[0][i]);
			   B.add(lockedPairs[1][i]);   
		   } 
	 }
	 
	 
   }	
   
   //quicksort D values
	public  ArrayList<Integer> quicksort(ArrayList<Integer> A){
		
		if(A.size() <= 1){
			return A;
		}
		
		
		
		int middle = (int) Math.ceil((double)A.size() / 2);
		int pivotD = D.get(A.get(middle)); // edit
		int pivot = A.get(middle);

		ArrayList<Integer> less = new ArrayList<Integer>();
		ArrayList<Integer> greater = new ArrayList<Integer>();
		
		for (int i = 0; i < A.size(); i++) {
			if(D.get(A.get(i)) >= pivotD){  // edit, replace
				if(i == middle){
					continue;
				}
				greater.add(A.get(i));
			}
			else{
				less.add(A.get(i));
			}
		}
		
		return concatenate(quicksort(greater), pivot, quicksort(less));
	}
	

	private ArrayList<Integer> concatenate(ArrayList<Integer> greater, int pivot, ArrayList<Integer> less){
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for (int i = 0; i < greater.size(); i++) {
			list.add(greater.get(i));
		}
		
		list.add(pivot);
		
		for (int i = 0; i < less.size(); i++) {
			list.add(less.get(i));
		}
		
		
		return list;
	}
   
   public void initializePartition(){
	   for(int i=0;i<n;i++){
		   if(Math.random()>0.5 && A.size()<n/2)A.add(i);
		   else if (B.size() < n/2) B.add(i);	
		   else i--;
       } 
	}
	
	
	
}
