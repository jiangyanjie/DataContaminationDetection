import openai
import requests
import os
from sklearn.metrics import accuracy_score
import csv
import argparse
from google.colab import drive


# Set your API keys for GPT-3.5 (OpenAI) and Claude 2.0 (Anthropic)
openai.api_key = 'your-openai-api-key-here'  # Replace with your OpenAI API key
claude_api_key = 'your-claude-api-key-here'  # Replace with your Claude API key

MAX_TOKENS = 512  # Token limit for each chunk

def count_tokens(text):
    # Approximate token count using spaces as a rough estimate
    return len(text.split())

def complete_code_with_api(text, model, use_api=False, max_tokens=150):
    if use_api:
        # Use GPT-3.5 (OpenAI) for code completion
        response = openai.Completion.create(
            model=model,
            prompt=text,
            max_tokens=max_tokens,
            temperature=0.7,
            top_p=1.0,
            frequency_penalty=0.0,
            presence_penalty=0.0
        )
        generated_code = response.choices[0].text
    else:
        # Use Claude 2.0 (Anthropic) for code completion
        headers = {
            "Authorization": f"Bearer {claude_api_key}",
            "Content-Type": "application/json"
        }
        data = {
            "model": "claude-2",
            "prompt": text,
            "max_tokens": max_tokens
        }
        response = requests.post("https://api.anthropic.com/v1/completions", headers=headers, json=data)
        response_json = response.json()
        generated_code = response_json['completion']
    
    return generated_code

def compute_token_accuracy(generated_code, ground_truth):
    # Tokenize the code and compute token-level accuracy
    gen_tokens = generated_code.split()
    true_tokens = ground_truth.split()

    accuracy = accuracy_score(true_tokens, gen_tokens)
    return accuracy

def process_in_chunks(input_code, ground_truth_code, model, use_api=False):
    result = ""
    input_tokens = count_tokens(input_code)
    
    # Process the input in multiple chunks of 512 tokens
    while input_tokens > 0:
        # Take the next 512 tokens (or less if at the end)
        chunk = input_code[:MAX_TOKENS]
        input_code = input_code[MAX_TOKENS:]  # Remove processed chunk from input
        
        # Adjust the remaining tokens for the next cycle
        remaining_tokens = min(MAX_TOKENS, input_tokens)
        
        # Get the completion for this chunk
        chunk_generated_code = complete_code_with_api(chunk, model=model, use_api=use_api, max_tokens=remaining_tokens)
        
        # Append the result of this chunk to the overall result
        result += chunk_generated_code
        input_tokens -= len(chunk.split())  # Reduce the remaining input tokens
    
    return result


def main(input_folder_path, output_file_path):
    # Prepare the data to write into a CSV file
    output_data = [["Text", "GPT-3.5 Token-level Accuracy", "Claude 2.0 Token-level Accuracy"]]

    # Loop through each file in the folder and process the code files
    for filename in os.listdir(input_folder_path):
        # Check if the file is a Java code file
        if filename.endswith(".java"):
            file_path = os.path.join(input_folder_path, filename)
            
            # Read the code file content
            with open(file_path, "r") as file:
                code = file.read()

            split_index = len(code) // 2
            input_code = code[:split_index]
            ground_truth_code = code[split_index:]

            # Process the code in multiple chunks
            gpt3_generated_code = process_in_chunks(input_code, ground_truth_code, model="gpt-3.5-turbo", use_api=True)
            claude_generated_code = process_in_chunks(input_code, ground_truth_code, model="claude-2", use_api=False)

            # Compute token-level accuracy for GPT-3.5
            gpt3_accuracy = compute_token_accuracy(gpt3_generated_code, ground_truth_code)

            # Compute token-level accuracy for Claude 2.0
            claude_accuracy = compute_token_accuracy(claude_generated_code, ground_truth_code)

            # Add the results to the output_data list
            output_data.append([filename, gpt3_accuracy, claude_accuracy])

    # Write the results to the CSV file specified by the user
    with open(output_file_path, mode="w", newline="") as file:
        writer = csv.writer(file)
        writer.writerows(output_data)

    print(f"Results have been saved to '{output_file_path}'.")


if __name__ == "__main__":
    # Set up argument parser
    parser = argparse.ArgumentParser(description="Process Java code files and compute token-level accuracy for GPT-3.5 and Claude 2.0.")
    parser.add_argument('input_folder', type=str, help="Path to the input folder containing Java files.")
    parser.add_argument('output_file', type=str, help="Path to save the output CSV file.")

    # Parse the arguments
    args = parser.parse_args()

    # Run the main function with the provided arguments
    main(args.input_folder, args.output_file)
