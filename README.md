# Diagno vision Services

Microservices for DiagnoVision Application

DiagnoVision is a multi-disease diagnosis service available to be used on Chest X-Ray images. This repository holds the source code for the source code for the web applications.
For the source code of the machine learning model please refer to: https://github.com/RikuAlter/cxr_ensemble_classifier

## Description

The web applications for DiagnoVision are built using Spring Framework.
Users can log in to the webapp using their email-id or via OAUTH2 and upload their images for analysis using the DiagnoVision Classifier. Users can view their library of uploaded images and view the reports generated against each image whenever they desire.

DiagnoVision offers an in-depth view of the diseases found and localized view of the affected zones with additional knowledge of the disease.
Currently diagnosed disease types: TBD

## Technologies Used
1. Java
2. Spring Boot
3. Kafka
4. Amazon S3
5. Docker
6. Amazon EKS

## Installation

You can download the source code for the Spring based webapp from this repository.
The necessary dependencies are updated in build.gradle

The following environment variables need to be made available to ensure proper database connection and Kafka functionality: TBD

## API Documentation
TBD

## Suggestions and Improvement Feedback
We are open to suggestions and improvements, please let us know of additional features/workaround by opening an issue against this repository or reaching out directly!

## Contact

Please feel free to open an issue against this repository anytime in order to raise an issue.
Or contact us directly at: rikuAlter@gmail.com

## Acknowledgements
TBD
