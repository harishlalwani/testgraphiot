'use strict';

var React = require('react');
var ReactDOM = require('react-dom');
var PieChart = require("react-chartjs").Pie;

var socket = io.connect();

var MyComponent = React.createClass({
	render() {
		return(
			<PieChart data={this.props.data} options={this.props.data} width="600" height="250" redraw/>
		);
	}
});

var GraphApp = React.createClass({

	getInitialState() {
		return {users: [], messages:[], text: '', chartData:[
          {
              value: 300,
              color: "#949FB1",
              highlight: "#A8B3C5",
              label: 300
          },
          {
              value: 40,
              color: "#949FB1",
              highlight: "#A8B3C5",
              label: 40
          },
          {
              value: 78,
              color: "#949FB1",
              highlight: "#A8B3C5",
              label: 78
          },
          {
              value: 20,
              color: "#949FB1",
              highlight: "#A8B3C5",
              label: 20
          },
          {
              value: 15,
              color: "#949FB1",
              highlight: "#A8B3C5",
              label: 15
          }
          ]};
	},

	componentDidMount() {
		socket.on('change:graph', this._graphChanged);
	},

	_graphChanged(data) {
		var {chartData} = this.state;
			chartData.push({
	        value: data,
	        color: "#949FB1",
            highlight: "#A8B3C5",
            label: data
	      });
		this.setState({chartData:chartData});
		this.forceUpdate();
	},

	render() {
		return (
			<div>
				<MyComponent 
					data={this.state.chartData}	
				/>
			</div>
		);
	}
});

ReactDOM.render(<GraphApp/>, document.getElementById('app'));