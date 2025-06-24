import pandas as pd
import numpy as np
from sklearn.preprocessing import LabelEncoder, StandardScaler
from sklearn.svm import SVC
from sklearn.metrics import accuracy_score, roc_auc_score, confusion_matrix
import argparse

# Function to evaluate the model and return metrics
def evaluate_model(classifier, X_train, X_test, y_train, y_test):
    # Fit the model on the training data
    classifier.fit(X_train, y_train)
    
    # Make predictions on the test data
    y_pred = classifier.predict(X_test)
    y_prob = classifier.predict_proba(X_test)[:, 1] if hasattr(classifier, 'predict_proba') else None
    
    # Calculate accuracy
    accuracy = accuracy_score(y_test, y_pred)
    
    # Calculate confusion matrix and derive TPR and FPR
    tn, fp, fn, tp = confusion_matrix(y_test, y_pred).ravel()
    tpr = tp / (tp + fn)  # True Positive Rate
    fpr = fp / (fp + tn)  # False Positive Rate
    
    # Calculate AUC
    auc = roc_auc_score(y_test, y_prob) if y_prob is not None else None
    
    return accuracy, tpr, fpr, auc

# Main function that will run the program
def main(train_path, test_path, output_path):
    # Define the classifier (only SVM)
    classifier = SVC(probability=True)

    # Load the training dataset
    train_data = pd.read_excel(train_path)

    # Select relevant columns and rename for clarity
    train_data_filtered = train_data[['performance', 'perturbation discrepancy', 'naturalness', 'source']]
    train_data_filtered.columns = ['performance', 'perturbation_discrepancy', 'naturalness', 'source']

    # Prepare the input features and output target for training
    X_train = train_data_filtered[['performance', 'perturbation_discrepancy', 'naturalness']]
    y_train = train_data_filtered['source']

    # Load the testing dataset
    test_data = pd.read_excel(test_path)

    # Select relevant columns and rename for clarity
    test_data_filtered = test_data[['performance', 'perturbation discrepancy', 'naturalness', 'source']]
    test_data_filtered.columns = ['performance', 'perturbation_discrepancy', 'naturalness', 'source']

    # Prepare the input features and output target for testing
    X_test = test_data_filtered[['performance', 'perturbation_discrepancy', 'naturalness']]
    y_test = test_data_filtered['source']

    # Encode the categorical target variable
    label_encoder = LabelEncoder()
    y_train_encoded = label_encoder.fit_transform(y_train)
    y_test_encoded = label_encoder.transform(y_test)

    # Scale the input features
    scaler = StandardScaler()
    X_train_scaled = scaler.fit_transform(X_train)
    X_test_scaled = scaler.transform(X_test)

    # Train the classifier and make predictions
    classifier.fit(X_train_scaled, y_train_encoded)
    y_pred = classifier.predict(X_test_scaled)

    # Add predictions to the testing data
    test_data_filtered['prediction'] = label_encoder.inverse_transform(y_pred)

    # Save the updated testing dataset with predictions to a new Excel file
    test_data_filtered.to_excel(output_path, index=False)

    print(f"Testing dataset with predictions has been saved to {output_path}")

# Command-line argument parsing
if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Train an SVM classifier and make predictions on the testing dataset")
    
    # Arguments for training path, testing path, and output path
    parser.add_argument("--train_path", required=True, help="Path to the training dataset")
    parser.add_argument("--test_path", required=True, help="Path to the testing dataset")
    parser.add_argument("--output_path", required=True, help="Path to save the output Excel file with predictions")

    # Parse arguments
    args = parser.parse_args()

    # Call the main function with the provided paths
    main(args.train_path, args.test_path, args.output_path)
