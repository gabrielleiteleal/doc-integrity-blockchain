//SPDX-License-Identifier: MIT
pragma solidity ^0.8.28;

contract ValidateDocumentHash {
    
    address public owner;

    struct Document {
        uint256 timestamp;
        address issuer;
        bool isRegistered;
    }

    mapping(bytes32 => Document) private documents;

    event DocumentRegistered(bytes32 indexed documentHash, address indexed issuer);

    constructor() {
        owner = msg.sender;
    }

    modifier onlyOwner() {
        require(msg.sender == owner, "Only the owner can perform this action.");
        _;
    }

    function registerDocument(bytes32 _documentHash) external onlyOwner {
        require(!documents[_documentHash].isRegistered, "Document hash is already registered.");

        documents[_documentHash] = Document({
            timestamp: block.timestamp,
            issuer: msg.sender,
            isRegistered: true
        });

        emit DocumentRegistered(_documentHash, msg.sender);
    }

    function verifyDocument(bytes32 _documentHash) external view returns (bool, uint256, address) {
        Document memory doc = documents[_documentHash];
        return (doc.isRegistered, doc.timestamp, doc.issuer);
    }

}