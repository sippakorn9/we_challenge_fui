import React, { Component } from 'react';
import { Card, CardColumns, Form, Col, Button, Container, Row, Jumbotron, InputGroup, Modal } from 'react-bootstrap'
import Parser from 'html-react-parser';

export class ReviewList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoaded: false,
            error: null,
            show: false,
            items: [],
            search: "1",
            select: 1,
            content: "",
        };
        this.handleChangeSearch = this.handleChangeSearch.bind(this);
        this.handleSubmitSearch = this.handleSubmitSearch.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.handleChangeEdit = this.handleChangeEdit.bind(this);
        this.handleSubmitEdit = this.handleSubmitEdit.bind(this);
        this.handleRequestToEdit = this.handleRequestToEdit.bind(this);
    }

    componentDidMount() {
        this.setState({ isLoaded: false });
        if (isNaN(this.state.search)) {
            this.searchByQuery()
        } else {
            this.searchById();
        }
    }

    searchById() {
        fetch("http://localhost:8080/reviews/" + this.state.search)
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                        items: [result]
                    });
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error
                    });
                }
            )
    }


    searchByQuery() {
        fetch("http://localhost:8080/reviews?query=" + this.state.search)
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                        items: result.reviews
                    });
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error
                    });
                }
            )
    }

    handleChangeSearch(event) {
        this.setState({ search: event.target.value });
    }

    handleSubmitSearch(event) {
        if (this.state.search) {
            if (isNaN(this.state.search)) {
                this.searchByQuery()
            } else {
                this.searchById();
            }
        } else {
            this.setState({
                isLoaded: true,
                items: []
            });
        }
        event.preventDefault();
    }

    handleClose() {
        this.setState({
            show: false,
        });
    }

    handleChangeEdit(event) {
        this.setState({ content: event.target.value });
    }

    handleSubmitEdit() {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ reviewContent: this.state.content })
        };
        fetch("http://localhost:8080/reviews/" + this.state.select, requestOptions)
            .then(data => this.setState({ show: false, items: []}));
    }

    handleRequestToEdit(event, item) {
        this.setState({
            show: true,
            content: item.reviewContent.replaceAll('<keyword>', '').replaceAll('</keyword>', ''),
            select: item.id,
        });
        event.preventDefault();
    }

    render() {
        const { error, isLoaded, items, search, show, select, content } = this.state;
        if (error) {
            return <div>Error: {error.message}</div>;
        } else if (!isLoaded) {
            return <div>Loading...</div>;
        } else {
            return (
                <Container>
                    <Jumbotron fluid>
                        <Container>
                            <h2>We-Challenge Project Website</h2>
                            <p>
                                This is website for We-Challenge Project.
                            </p>
                            <a href="https://github.com/sippakorn9/we_challenge_fui">Link to repository on GitHub</a>
                        </Container>
                    </Jumbotron>
                    <Row>
                        <Col />
                        <Col>
                            <form onSubmit={this.handleSubmitSearch}>
                                <InputGroup className="mb-3">
                                    <Form.Control
                                        className="mb-3"
                                        id="inlineFormInput"
                                        placeholder="Review ID or (Food) Keyword"
                                        value={this.state.search} onChange={this.handleChangeSearch}
                                    />
                                    <InputGroup.Append>
                                        <Button variant="outline-primary" type="submit" className="mb-3">
                                            Search
                                </Button>
                                    </InputGroup.Append>
                                </InputGroup>
                            </form>
                        </Col >
                        <Col />
                    </Row >
                    <br />
                    <CardColumns className="align-items-center">
                        {items.map(item => (
                            <Card bg='light'
                                key={item.id}
                                text='dark'
                                style={{ width: '18rem' }}
                                className="mb-2">
                                <Card.Header>{item.id}</Card.Header>
                                <Card.Body>
                                    <Card.Text>
                                        {Parser(item.reviewContent.replaceAll('keyword', 'b'))}
                                    </Card.Text>
                                    <Button variant="outline-primary" onClick={e => this.handleRequestToEdit(e, item)}>Edit</Button>
                                </Card.Body>
                            </Card>
                        ))}
                    </CardColumns>
                    <Modal show={show} onHide={this.handleClose} animation={false}>
                        <Modal.Header closeButton>
                            <Modal.Title>Edit review : {select}</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            <Form.Group controlId="reviewEditForm.TextArea">
                                <Form.Control as="textarea" rows={20} value={this.state.content} onChange={this.handleChangeEdit} />
                            </Form.Group>
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="secondary" onClick={this.handleClose}>
                                Close
                            </Button>
                            <Button variant="primary" onClick={this.handleSubmitEdit}>
                                Save Changes
                            </Button>
                        </Modal.Footer>
                    </Modal>
                </Container >
            );
        }
    }
}

export default ReviewList;