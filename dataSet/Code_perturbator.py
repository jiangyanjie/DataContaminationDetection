import os
import random
import numpy as np
import argparse

# Helper function to insert spaces at random locations in the code
def insert_spaces(code, alpha=0.5, lambda_spaces=3):
    C = get_space_locations(code)
    Cs = random.sample(C, k=int(alpha * len(C)))  # Randomly select subset Cs
    for c in Cs:
        nspaces = np.random.poisson(lambda_spaces)  # Poisson distribution for random spaces
        code = insert_at_location(code, c, nspaces)
    return code

# Helper function to insert newlines at random locations in the code
def insert_newlines(code, beta=0.5, lambda_newlines=2):
    lines = code.split('\n')
    L = list(range(len(lines)))  # Line locations
    Ln = random.sample(L, k=int(beta * len(L)))  # Randomly select subset Ln
    for l in Ln:
        nnewlines = np.random.poisson(lambda_newlines)  # Poisson distribution for newlines
        lines = insert_newlines_at_line(lines, l, nnewlines)
    return '\n'.join(lines)

# Main perturbation function that selects space or newline insertion
def perturb_code(code, alpha=0.5, beta=0.5, lambda_spaces=3, lambda_newlines=2):
    p = random.random()
    if p <= 0.5:
        perturbed_code = insert_spaces(code, alpha, lambda_spaces)
    else:
        perturbed_code = insert_newlines(code, beta, lambda_newlines)
    return perturbed_code

# Example function for space location retrieval (stub)
def get_space_locations(code):
    return [i for i, char in enumerate(code) if char == ' ']

# Example function for inserting spaces (stub)
def insert_at_location(code, location, nspaces):
    return code[:location] + ' ' * nspaces + code[location:]

# Example function for inserting newlines (stub)
def insert_newlines_at_line(lines, line_idx, nnewlines):
    lines.insert(line_idx, '\n' * nnewlines)  # Insert nnewlines after the selected line
    return lines

# Function to process files in the given directory
def process_files(input_dir, output_dir, num_perturbations=50, alpha=0.5, beta=0.5, lambda_spaces=3, lambda_newlines=2):
    # Loop over all the Java files in the input directory
    for filename in os.listdir(input_dir):
        if filename.endswith(".java"):
            # Construct the full path to the input file
            input_file_path = os.path.join(input_dir, filename)
            
            # Read the contents of the input .java file
            with open(input_file_path, 'r', encoding='utf-8') as file:
                code = file.read()
            
            # Process the code with perturbations
            perturbed_code = perturb_code(code, alpha, beta, lambda_spaces, lambda_newlines)
            
            # Construct the full path for saving the output file
            output_file_path = os.path.join(output_dir, filename)
            
            # Save the perturbed code to the output file
            with open(output_file_path, 'w', encoding='utf-8') as file:
                file.write(perturbed_code)
            print(f"Processed and saved: {filename}")

# Main function to handle argument parsing and execution
def main():
    parser = argparse.ArgumentParser(description='Perturb Java code by inserting spaces or newlines')
    parser.add_argument('input_folder', type=str, help='Path to folder containing input Java files')
    parser.add_argument('--output_folder', type=str, default='output', help='Path to save the perturbed Java files')
    parser.add_argument('--n_perturbations', type=int, default=50, help='Number of perturbations to apply')
    parser.add_argument('--alpha', type=float, default=0.5, help='Alpha value for space perturbation')
    parser.add_argument('--beta', type=float, default=0.5, help='Beta value for newline perturbation')
    parser.add_argument('--lambda_spaces', type=int, default=3, help='Lambda for Poisson distribution of spaces')
    parser.add_argument('--lambda_newlines', type=int, default=2, help='Lambda for Poisson distribution of newlines')

    args = parser.parse_args()

    # Validate input folder
    if not os.path.exists(args.input_folder):
        print(f"Error: Input folder '{args.input_folder}' does not exist!")
        exit(1)
    
    print(f"\n{'='*40}")
    print(f"Input folder: {args.input_folder}")
    print(f"Output folder: {args.output_folder}")
    print(f"Perturbation rounds: {args.n_perturbations}")
    print(f"Alpha (spaces): {args.alpha}")
    print(f"Beta (newlines): {args.beta}")
    print(f"Lambda (spaces): {args.lambda_spaces}")
    print(f"Lambda (newlines): {args.lambda_newlines}")
    print(f"{'='*40}\n")
    
    # Call the function to process the files
    process_files(
        args.input_folder,
        args.output_folder,
        num_perturbations=args.n_perturbations,
        alpha=args.alpha,
        beta=args.beta,
        lambda_spaces=args.lambda_spaces,
        lambda_newlines=args.lambda_newlines
    )

if __name__ == "__main__":
    main()
