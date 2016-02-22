1. The execution time to create input.cfg - OK
		/**RESULT**/
		//219 MILLISECONDS
		//0 SECONDS
		/***RESULT***/

2. The execution time to RDFize Solver output UNV - OK
		/**RESULT**/
		//2050 MILLISECONDS
		//2 SECONDS
		/***RESULT***/
		
3. The execution time to RDFize Solver output DAT - OK
		/**RESULT**/
		//5308 MILLISECONDS
		//5 SECONDS
		/***RESULT***/
		
4. The execution time of solver - OK
		/**RESULT**/
		//74233 MILLISECONDS
		//74 SECONDS
		/***RESULT***/

5. If possible, could also determine the execution time for data analysis - OK
		/**RESULT**/
		//1038 MILLISECONDS
		//1 SECONDS
		/***RESULT***/

6. The execution time for RDFizing data analysis output - It's happening in atomic way, once the service was called. So the time execution
from item five, is global for data analysis, as well as RDFizing the result.

7. The time it takes to query from virtuoso - OK
			/**RESULT**/
			//432817 MILLISECONDS
			//432 SECONDS
			/***RESULT***/

8. The time it takes to query inputstream jena model - OK
			/**RESULT**/
			//16603 MILLISECONDS
			//16 SECONDS
			/***RESULT***/
			
9. time it takes to query the cache (indexed data) on mongoDB - OK
			/**RESULT**/
			//509 MILLISECONDS
			//0 SECONDS
			/***RESULT***/
