import torch
import re
import numpy as np
import os
import argparse
from transformers import AutoTokenizer, AutoModelForSeq2SeqLM
from tqdm import tqdm

DEVICE = "cuda" if torch.cuda.is_available() else "cpu"
pattern = re.compile(r"<extra_id_\d+>")

class TextPerturbator:
    def __init__(self, model_name='t5-large'):
        self.model = AutoModelForSeq2SeqLM.from_pretrained(model_name).to(DEVICE)
        self.tokenizer = AutoTokenizer.from_pretrained(model_name)
        self.mask_string = '<<<mask>>>'
        
    def tokenize_and_mask(self, text, span_length=2, pct=0.3):
        tokens = text.split()
        buffer_size = 1  # Words around mask to avoid overlapping
        n_spans = int(pct * len(tokens) / (span_length + 2 * buffer_size))
        n_spans = max(1, int(np.ceil(n_spans)))

        for _ in range(n_spans):
            while True:
                start = np.random.randint(0, len(tokens) - span_length)
                end = start + span_length
                # Check buffer zones
                search_start = max(0, start - buffer_size)
                search_end = min(len(tokens), end + buffer_size)
                if self.mask_string not in tokens[search_start:search_end]:
                    tokens[start:end] = [self.mask_string]
                    break
        
        num_filled = 0
        for idx in range(len(tokens)):
            if tokens[idx] == self.mask_string:
                tokens[idx] = f'<extra_id_{num_filled}>'
                num_filled += 1
        return ' '.join(tokens)

    def replace_masks(self, masked_texts):
        n_expected = [len(re.findall(pattern, text)) for text in masked_texts]
        stop_id = self.tokenizer.encode(f"<extra_id_{max(n_expected)}>")[0]
        
        inputs = self.tokenizer(
            masked_texts, 
            return_tensors="pt", 
            padding=True,
            truncation=True,
            max_length=512
        ).to(DEVICE)
        
        outputs = self.model.generate(
            **inputs,
            max_length=150,
            do_sample=True,
            top_p=0.96,
            eos_token_id=stop_id
        )
        return self.tokenizer.batch_decode(outputs, skip_special_tokens=False)

    def perturb(self, text, n_perturbations=1, span_length=2, pct=0.3):
        perturbed_text = text
        for _ in range(n_perturbations):
            masked = self.tokenize_and_mask(perturbed_text, span_length, pct)
            filled = self.replace_masks([masked])[0]
            
            extracted = pattern.split(filled)[1:-1]
            extracted = [t.strip() for t in extracted]
            
            tokens = masked.split()
            for i in range(len(extracted)):
                mask_idx = tokens.index(f'<extra_id_{i}>')
                tokens[mask_idx] = extracted[i]
            
            perturbed_text = ' '.join(tokens)
        return perturbed_text

def process_files(input_path, output_path, perturber, n_perturbations=2, span_length=2, pct=0.3):
    # Create output directory if it doesn't exist
    os.makedirs(output_path, exist_ok=True)
    
    # Get all txt files in input directory
    txt_files = [f for f in os.listdir(input_path) if f.endswith('.txt')]
    
    if not txt_files:
        print(f"No .txt files found in {input_path}")
        return
    
    for filename in tqdm(txt_files, desc="Processing files"):
        input_file = os.path.join(input_path, filename)
        output_file = os.path.join(output_path, filename)
        
        try:
            with open(input_file, 'r', encoding='utf-8') as f:
                original_text = f.read()
            
            perturbed_text = perturber.perturb(
                original_text,
                n_perturbations=n_perturbations,
                span_length=span_length,
                pct=pct
            )
            
            with open(output_file, 'w', encoding='utf-8') as f:
                f.write(perturbed_text)
                
        except Exception as e:
            print(f"\nError processing {filename}: {str(e)}")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description='Text Perturbation Tool')
    parser.add_argument('input_folder', type=str, help='Path to folder containing input text files')
    parser.add_argument('--output_folder', type=str, default='perturbed_output',
                       help='Output folder path (default: perturbed_output)')
    parser.add_argument('--n_perturb', type=int, default=2,
                       help='Number of perturbation rounds (default: 2)')
    parser.add_argument('--span_length', type=int, default=2,
                       help='Length of text spans to mask (default: 2)')
    parser.add_argument('--mask_pct', type=float, default=0.3,
                       help='Percentage of text to mask (default: 0.3)')
    parser.add_argument('--model', type=str, default='t5-large',
                       help='Pretrained model name (default: t5-large)')
    
    args = parser.parse_args()
    
    if not os.path.exists(args.input_folder):
        print(f"Error: Input folder '{args.input_folder}' does not exist!")
        exit(1)
    
    print(f"\n{'='*40}")
    print(f"Initializing perturbator with {args.model}")
    print(f"Input folder: {args.input_folder}")
    print(f"Output folder: {args.output_folder}")
    print(f"Perturbation rounds: {args.n_perturb}")
    print(f"Span length: {args.span_length}")
    print(f"Mask percentage: {args.mask_pct}")
    print(f"{'='*40}\n")
    
    perturber = TextPerturbator(model_name=args.model)
    process_files(
        args.input_folder,
        args.output_folder,
        perturber,
        n_perturbations=args.n_perturb,
        span_length=args.span_length,
        pct=args.mask_pct
    )
    print("\nPerturbation complete!")