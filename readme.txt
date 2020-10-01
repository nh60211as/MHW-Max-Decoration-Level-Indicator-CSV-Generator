Source code to generate "Max Decoration Level Indicator (Updated)" for Monster Hunter: World: https://www.nexusmods.com/monsterhunterworld/mods/1088

Make sure you have Java Runtime Environment installed and path added to System variable.

How to use the program
type the following into the command propmt
java -jar MHW_Max_Decor_CSV.jar "input file name" 1
or
java -jar MHW_Max_Decor_CSV.jar "input file folder" 1

If the input argument is a single file, the generated "patch file" will be named something like item_chT_patch_1008_MDLI.csv.

If the input argument is a folder, the program will scan the files with the extension "csv" in the folder and generate the corresponding files in the folder named <original file folder>_remain.

The second argument is the variant of the indicator
    // Example: Offensive Guard [3], Attack Boost [7]
    // method 1: Higher level needed -> max(3,7) -> [7]
    // method 2: Separate numbers                -> [3,7]
    // method 3: Lower level needed  -> min(3,7) -> [3]
Example:
java -jar MHW_Max_Decor_CSV.jar item_chT.csv 1
will get you a variant 1 item_chT_patch_1008_MDLI.csv for you to import to GMD Editor.

How to add new skills and jewls
Edit skillList.txt and jewelList.txt according to their format
Note that the second column of jewelList.txt (Antidote Jewel 1) does not do anything. It's just used to check for error.

Libraries Used
Opencsv to parse csv files
http://opencsv.sourceforge.net/
