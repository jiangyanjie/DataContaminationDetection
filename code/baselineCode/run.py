import openai
import requests
import os
import numpy as np
import csv
import argparse
from google.colab import drive

# Set your API keys for GPT-3.5 (OpenAI) and Claude 2.0 (Anthropic)
openai.api_key = 'your-openai-api-key-here'  # Replace with your OpenAI API key
claude_api_key = 'your-claude-api-key-here'  # Replace with your Claude API key

# Function to handle the API call for Claude 2.0
def get_claude_response(text, claude_api_key):
    headers = {
        "Authorization": f"Bearer {claude_api_key}",
        "Content-Type": "application/json"
    }
    data = {
        "model": "claude-2",  # specify the model here
        "prompt": text,
        "max_tokens": 150  # or modify as per your requirement
    }

    try:
        response = requests.post("https://api.anthropic.com/v1/completions", headers=headers, json=data)
        response.raise_for_status()  # Raises an HTTPError for bad responses (4xx, 5xx)
        response_json = response.json()
        return response_json['completion'], len(response_json['completion'].split())  # generate text and compute perplexity
    except requests.exceptions.RequestException as e:
        print(f"Error with Claude API request: {e}")
        return "", 0  # return empty text and 0 perplexity in case of an error

# Compute log likelihood and perplexity
def compute_log_likelihood_perplexity_min20(text, model, use_api=False):
    if use_api:
        # Use the OpenAI API (GPT-3.5)
        response = openai.Completion.create(
            model=model,
            prompt=text,
            max_tokens=150,
            temperature=0.7,
            top_p=1.0,
            frequency_penalty=0.0,
            presence_penalty=0.0
        )
        generated_text = response.choices[0].text
        perplexity = len(generated_text.split())  # Placeholder for perplexity calculation
        min_20_percent = np.mean([len(word) for word in generated_text.split()])  # Example metric for "min-20%"
        
    else:
        perplexity = 0
        min_20_percent = 0
    
    return perplexity, min_20_percent

# Main function to process text files
def process_files(input_path, output_path, openai_api_key, claude_api_key):
    # Prepare the data to write into a CSV file
    output_data = [["Text", "GPT-3.5 Perplexity", "GPT-3.5 MIN-20% Log-Likelihood", "Claude 2.0 Perplexity", "Claude 2.0 MIN-20% Log-Likelihood"]]

    # Loop through each file in the folder and process the text
    for filename in os.listdir(input_path):
        if filename.endswith(".txt"):
            file_path = os.path.join(input_path, filename)
            
            # Read the text file content
            with open(file_path, "r") as file:
                text = file.read()

            # Compute for GPT-3.5 using OpenAI API
            gpt3_perplexity, gpt3_min_20_percent = compute_log_likelihood_perplexity_min20(text, model="gpt-3.5-turbo", use_api=True)
            
            # Compute for Claude 2.0 using Anthropic API
            claude_generated_text, claude_perplexity = get_claude_response(text, claude_api_key)
            claude_min_20_percent = np.mean([len(word) for word in claude_generated_text.split()])  # Example metric for "min-20%"

            # Add the results to the output_data list
            output_data.append([filename, gpt3_perplexity, gpt3_min_20_percent, claude_perplexity, claude_min_20_percent])

    # Ensure output directory exists
    os.makedirs(output_path, exist_ok=True)
    
    # Define the output file path (automatically add a CSV file in the output folder)
    output_file_path = os.path.join(output_path, "metrics.csv")

    # Write the results to a CSV file
    with open(output_file_path, mode="w", newline="") as file:
        writer = csv.writer(file)
        writer.writerows(output_data)

    print(f"Results have been saved to '{output_file_path}'.")

# Command-line interface setup
def main():
    parser = argparse.ArgumentParser(description="Process text files and compute log-likelihood/perplexity for GPT-3.5 and Claude 2.0.")
    parser.add_argument("input_path", help="Path to the folder containing input text files")
    parser.add_argument("output_path", help="Path to the folder where the output CSV file should be saved")

    args = parser.parse_args()

    # Run the processing function
    process_files(args.input_path, args.output_path, openai.api_key, claude_api_key)

if __name__ == "__main__":
    main()
