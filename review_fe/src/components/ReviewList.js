import React, { Component } from 'react';
import { Card, CardColumns, Form, Col, Button, Container, Row, Jumbotron, InputGroup } from 'react-bootstrap'
import Parser from 'html-react-parser';

export class ReviewList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoaded: false,
            error: null,
            items: [],
            search: "1",
            select: 1,
            content: "",
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
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

    handleChange(event) {
        this.setState({ search: event.target.value });
    }

    handleSubmit(event) {
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

    render() {
        const { error, isLoaded, items } = this.state;
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
                            <form onSubmit={this.handleSubmit}>
                                <InputGroup className="mb-3">
                                    <Form.Control
                                        className="mb-3"
                                        id="inlineFormInput"
                                        placeholder="Review ID or (Food) Keyword"
                                        defaultValue="1"
                                        value={this.state.value} onChange={this.handleChange}
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
                                    <Button variant="outline-primary">Edit</Button>
                                </Card.Body>
                            </Card>
                        ))}
                    </CardColumns>
                </Container >
            );
        }
    }
}

export default ReviewList;