# Portfolio project IDATT2003 - 2024

### STUDENT NAME = "Kevin Dennis Mazali"
### STUDENT NAME = "Kaamya Shinde"

## project description

The project is a Chaos Game simulator App. 
The intented target group for this project is ...

### Functionality of the program:
- Create, visualize, and modify fractals through the GUI.
- Read and save fractals to and from files.
- Adjust visual and technical parameters such as:
   - Number of steps/iterations in the chaos game
   - Minimum and maximum coordinates
   - The constant c for Julia sets
   - The matrix A and vector b for affine transformations

Scale the window in which the fractal is displayed

## Project Structure

The Project is divided in the 2 main directories 'main' & 'test' they respectively contain the program and the testing for the program.
the test directory is where the JUnit-test classes are stored while the different packages that make up the functionality of the project
are within the 'main' directory. These include; 'controller,' 'model,' 'view,' and 'resources'

## Link to repository
[https://gitlab.stud.idi.ntnu.no/kamyaas/idatt2003-grp1-chaosgame
](https://github.com/kaamyashinde/ChaosGame)

## How to run the tests
The tests are run by running the respective test packages for the model subpackages
these are 'basicLinAlg', 'engine' and 'transformations'


## How to run the application
The application is run from Maven by executing mvn javafx:run on the command line. It is also possible to add a Maven configuration in the development environment and run mvn javafx:run from there.
 



