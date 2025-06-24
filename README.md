# Data Contamination Detection in the Era of Large Language Models

## Content of the replication package

/dataSet: data to replicate the evaluation in the paper

/code: The implementation to replicate the empirical study

/results.xlsx: The experimental results reported in the paper

## dataSet Overview

/Bookmia: text dataset

/wikimia: text dataset

/sourcecode: source code dataset

### The steps to prepare for training classifier

1. Get the data completion accuracy

    `python Codecompletion_accuracy.py /home/user/java_files /home/user/results/output.csv`

    `python Textcompletion_accuracy.py /path/to/your/input/folder /path/to/your/output/folder/output.csv`

3. Get the data perturbation

   `python Text_perturbator.py /path/to/your/input/folder --output_folder /path/to/output/folder --n_perturb 3 --span_length 3 --mask_pct 0.5 --model t5-base`

   `python Code_perturbator.py /path/to/input/folder --output_folder /path/to/output/folder --n_perturbations 50 --alpha 0.5 --beta 0.5 -- lambda_spaces 3 --lambda_newlines 2`

4. Get the data naturalness

   we reuse the existing code, `https://bitbucket.org/tuzhaopeng/cachelm_for_code_suggestion/src/master/readme.txt`


## Code Overview

/baselineCode: The implementation of baseline approach

/SVM: The implementation of classifier

### The steps to run baseline approach and our approach

1. run baseline approach

    `python run.py /path/to/input /path/to/output`

2. run our approach

    `python classifier.py --train_path "path_to_train_file.xlsx" --test_path "path_to_test_file.xlsx" --output_path "path_to_save_predictions.xlsx"`

   
   








