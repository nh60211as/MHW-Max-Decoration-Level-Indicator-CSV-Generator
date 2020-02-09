Make sure you have Java Runtime Environment installed and path added to System variable.

How to use the program
type the following into the command propmt
java -jar MHW_Max_Decor_CSV.jar "input file name"
or
java -jar MHW_Max_Decor_CSV.jar "input file folder"

If the input argument is a single file, the generated "remain file" will be named something like item_chT_remain.csv.

If the input argument is a folder, the program will scan the files with the extension "csv" in the folder and generate the corresponding files in the folder named <original file folder>_remain.

Add argument "generateRemainKeyEntry" if you wish to get a new remainKeyEntry.txt when there are new set bonuses or new jewels added to the game.
Example:
java -jar MHW_Max_Decor_CSV.jar item_chT.csv generateRemainKeyEntry
will get you a new remainKeyEntry.txt and item_chT_remain.csv for you to import to GMD Editor.


How to add new skills and jewls
Edit skillList.txt and jewelList.txt according to their format
Note that the second column of jewelList.txt (Antidote Jewel 1) does not do anything. It's just used to check for error.
