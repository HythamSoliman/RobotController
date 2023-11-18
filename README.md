# RobotController
Built by Java openjdk version "21.0.1" 2023-10-17

## Source Code
- comments marked with [SR], means [Sheet Requirments]

# to compile and run the application from the command line, please run the below
cd path/to/RobotController                      # navigate to the project folder
javac -d target/ src/*java src/osone/*java      # Compilation
cd target && java osone/Controller && cd ..     # run the application

# line endings inside the batch files may cause a problem across different platforms, so we do provide 2 files for windows & unix execution, default is unix

# to execute the build script
bash ./build.sh                                 # if you face a problem please follow the below steps
chmod +x build.sh                               # grant execute permission to your script using the chmod command
xattr build.sh                                  # Check if the script has extended attributes that might prevent its execution
xattr -c build.sh                               # If you see any attributes, you can remove them
bash ./build.sh                                 # executing the script using bash explicitly

# to execute the run script
bash ./run.sh                                   # if you face a problem please follow the below steps
chmod +x run.sh                                 # grant execute permission to your script using the chmod command
xattr run.sh                                    # Check if the script has extended attributes that might prevent its execution
xattr -c run.sh                                 # If you see any attributes, you can remove them
bash ./run.sh                                   # executing the script using bash explicitly
