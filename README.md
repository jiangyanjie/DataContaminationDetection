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

2. Get the data perturbation

   `python Text_perturbator.py /path/to/your/input/folder --output_folder /path/to/output/folder --n_perturb 3 --span_length 3 --mask_pct 0.5 --model t5-base`

   `python Code_perturbator.py /path/to/input/folder --output_folder /path/to/output/folder --n_perturbations 50 --alpha 0.5 --beta 0.5 -- lambda_spaces 3 --lambda_newlines 2`

3. Get the data naturalness

   we reuse the existing code, `https://bitbucket.org/tuzhaopeng/cachelm_for_code_suggestion/src/master/readme.txt`
