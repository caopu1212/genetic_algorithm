# This is a personal code backup project.

-------------------------------------------------------------------------------------------



The code for the paper "Symbolic Regression Using Genetic Programming with Chaotic Method-based Probability Mappings" presented at the 13th International Conference on Frontier Computing (FC 2023) can be found at: genetic_algorithm\GP\src\SymbolicRegression

Project structure is as follows:
  
├─GP  
│ ├─dataset  
│ ├─output  
│ ├─src  
│ │ ├─main  
│ │ │ ├─java  
│ │ │ └─resources  
│ │ ├─SymbolicRegression  
│ │ │ ├─data  
│ │ │ ├─Elements  
│ │ │ ├─gp  
│ │ │ ├─TempCode  
│ │ │ ├─tools  
│ │ │ └─tree  
│ │ │ └─tempNode  


The script can be run from: genetic_algorithm\GP\src\SymbolicRegression\gp

Among them,

`genetic_algorithm\GP\src\SymbolicRegression\gp\BaseGenetic_MutiDimensionData.java`

is the main logic implementation for genetic programming. Others are different implementations for research experiments. Simply run the corresponding scripts.

`genetic_algorithm\GP\src\SymbolicRegression\tree`

is the implementation of the tree. By using Java reflection mechanism, if you need to add a new node implementation, you only need to write a new implementation file for the node, without modifying the code in the logic script.

The script for reading data is located at `genetic_algorithm\GP\src\SymbolicRegression\tools\FileOperator.java`. If you need to change the data used, you need to modify the absolute address in the above script (due to package issues, relative paths cannot be used to read data).

The dataset is located at `genetic_algorithm\GP\dataset`. The last column of the data represents the label.
